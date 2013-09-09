arm-none-linux-gnueabi-gcc hello.c -o hellostatic -static
g:\tools\adb push hellostatic /data/ch13
g:\tools\adb shell "chmod 777 /data/ch13/hellostatic"


