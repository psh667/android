arm-none-linux-gnueabi-gcc -c daytime.c
arm-none-linux-gnueabi-gcc -c -o crt0.o crt.S
arm-none-linux-gnueabi-ld --entry=_start --dynamic-linker /system/bin/linker -nostdlib -rpath /system/lib -rpath-link \android\system\lib -L \android\system\lib -l c -l android_runtime -l sqlite -o daytime daytime.o crt0.o
C:\software\google\android-sdk-windows-1.0_r1\tools\adb push daytime /data/ch13
C:\software\google\android-sdk-windows-1.0_r1\tools\adb shell "chmod 777 /data/ch13/daytime"
