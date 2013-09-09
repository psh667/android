/*
	Unlocking Android
	Chapter 13
	Author: Frank Ableson
	fableson@msiservices.com
*/
#include <time.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <resolv.h>
#include "sqlite3.h"

int PORTNUMBER = 1024;

// define this as a macro as we cannot seem to link it successfully for Android
#define htons(a) ( ((a & 0x00ff) << 8) | ((a & 0xff00) >> 8))

extern FILE *stderr;

void RecordHit(char * when)
{
  int rc;
	sqlite3 *db;
	char *zErrMsg = 0;
	char sql[200];
	
	
	
  rc = sqlite3_open("daytime_db.db",&db);
  if( rc )
  {
  	printf("cannot open db?\n");
    printf( "Can't open database: %s\n", sqlite3_errmsg(db));
    sqlite3_close(db);
    return;
  }
  
  bzero(sql,sizeof(sql));
  sprintf(sql,"insert into hits values (DATETIME('NOW'),'%s');",when);
  
  printf("RecordHit: %s\n",sql);
  
  //rc = sqlite3_exec(db, sql, callback, 0, &zErrMsg);
  rc = sqlite3_exec(db, sql, NULL, 0, &zErrMsg);
  if( rc!=SQLITE_OK )
  {
    printf( "SQL error: %s\n", zErrMsg);
  }
  
  sqlite3_close(db);
}

int main(int argc, char **argv)
{
int listenfd, connfd;
struct sockaddr_in servaddr;
char buf[100];
time_t ticks;
int done = 0;
int rc;
fd_set readset;
int result;
struct timeval tv;

	printf("Daytime Server\n");
	
	listenfd = socket(AF_INET,SOCK_STREAM,0);
	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = INADDR_ANY;
	servaddr.sin_port = htons(PORTNUMBER);

	rc = bind(listenfd, (struct sockaddr  *) &servaddr,sizeof(servaddr));
	if (rc != 0)
	{
		printf("after bind,rc = [%d]\n",rc);
		return rc;
	}
	
	listen(listenfd,5);
	while (!done)
	{
		printf("Waiting for connection\n");
		while (1)
		{
	    bzero(&tv,sizeof(tv));
	    tv.tv_sec = 2;
			FD_ZERO(&readset);
			FD_SET(listenfd, &readset);
			result = select(listenfd + 1, &readset, &readset, NULL, &tv);
		  if (result >= 1)
			{
				printf("Incoming connection!\n");
				break;
			}
			else if (result == 0)
			{
					printf("Timeout.\n");
					continue;
			}
			else 
			{
					printf("Error, leave.\n");
					return result;
			}
		} 
	
		printf("Calling accept:\n");
	  connfd = accept(listenfd,(struct sockaddr *) NULL, NULL);
	  printf("Connecting\n");
	  ticks = time(NULL);
	  sprintf(buf,"%.24s",ctime(&ticks));
	  printf("sending [%s]\n",buf);
	  write(connfd,buf,strlen(buf));
	  close(connfd);
	  RecordHit(buf);
	}
	return 0;
}

