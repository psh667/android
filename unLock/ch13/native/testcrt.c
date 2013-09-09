        #include <stdio.h>
        #include <unistd.h>
        
        int main(int argc, char **argv, char**env) {
                int i;
        
                for (i = 0; i < argc; i++) {
                        printf("argv[%d] = %s\n", i, argv[i]);
                }
                for (i = 0; env[i] != NULL; i++) {
                        printf("env[%d] = %s\n", i, env[i]);
                }
                printf("PATH = %s\n", getenv("PATH"));
                setenv("HELLO", "world!", 1);
                putenv("GOODBYE=cruel world!");
                printf("HELLO = %s\n", getenv("HELLO"));
                printf("GOODBYE = %s\n", getenv("GOODBYE"));
        
                return 0;
        }