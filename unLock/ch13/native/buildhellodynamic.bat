arm-none-linux-gnueabi-gcc -c hello.c -o hello.o
arm-none-linux-gnueabi-ld -entry=main -dynamic-linker /system/bin/linker -nostdlib -rpath /system/lib -rpath-link /android/system/lib -L /android/system/lib -l android_runtime -l c  -o hellodynamic hello.o
g:\tools\adb push hellodynamic /data/ch13
g:\tools\adb shell "chmod 777 /data/ch13/hellodynamic"
