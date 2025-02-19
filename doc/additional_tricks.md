## tricks for Nexus 9(volantis)

**volantis** has a dummy header of size 256 bytes, which looks like this:

    0000000: 78 56 34 12 00 00 00 00 00 ba 86 00 00 01 00 00  xV4.............
    0000010: 00 01 00 00 00 b8 86 00 00 b9 86 00 00 01 00 00  ................
    0000020: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    0000030: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    0000040: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    0000050: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    0000060: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    0000070: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    0000080: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    0000090: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    00000a0: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    00000b0: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    00000c0: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    00000d0: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    00000e0: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    00000f0: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................
    0000100: 41 4e 44 52 4f 49 44 21 72 64 6d 00 00 80 00 10  ANDROID!rdm.....
    0000110: d0 41 19 00 00 00 00 11 00 00 00 00 00 00 f0 10  .A..............

We have to trim the header before it can be recognized by our toy.

    $ dd if=boot.img of=raw_boot bs=256 skip=1

Now we can work with 'raw\_boot'

    $ cp raw_boot boot.img
    $ gradle unpack
    $ gradle pack

## Pixel XL (marlin)

**marlin** is a profile that adopts A/B system schema while still using Verified Boot 1.0 style boot image.

Due to the configuration "BOARD_BUILD_SYSTEM_ROOT_IMAGE := true", the embeded ramdisk in boot.img is actually used in recovery mode.

## Pixel 3 (blueline)

Fickle Google removed "BOARD_BUILD_SYSTEM_ROOT_IMAGE" and added "ro.boot.dynamic_partitions=true", which means normal mode ramdisk is back. Besides, it also packed DTB inside boot.img.

## NX606J

Thanks to the work by [CallMESuper], ZTE NX606J boot.img is also verified to be compatible with this toolkit.

ROM download page: [http://ui.nubia.cn/rom/detail/56](http://ui.nubia.cn/rom/detail/56)

## K3 (CPH1955)

`boot.img` extracted from OTA zip file doesn't work properly but `recovery.img` works fine. In order to obtain `recovery.img`, a `bsdiff` patch from `system/recovery-from-boot.p` is applied to `boot.img`. Ex: ```bspatch boot.img recovery.img system/recovery-from-boot.p```

This part is contributed by @Surendrajat, thanks!

## about porting

#### libsparse: output\_file.cpp

*typeof* is missing in macos clang++, need to change it to *decltype* instead.
