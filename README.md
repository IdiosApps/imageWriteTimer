# imageWriteTimer
How can you write big images to disk faster?

My test images are made by using [ImageMagick's](https://www.imagemagick.org/) convert tool, on [this excellently large railway schematic by the Indian government](https://ncr.indianrailways.gov.in/uploads/files/1420009273482-Agorikhas%2027-06.jpg). It looks like a limitation in Java (number of calls to get byte(s) from BufferedImage?), rather than an I/O speed limitation.

## Writing to SSD (Samsung 850 EVO 250GB) ##
```
imageIOWriteImage      | Time to write jpg: 1058 ms.
byteArrayWriteImage    | Time to write jpg: 1040 ms.
bufferChannelWriteImage| Time to write jpg: 1035 ms.
filesWriteImage        | Time to write jpg: 1028 ms.
imageIOWriteImage      | Time to write png: 2878 ms.
byteArrayWriteImage    | Time to write png: 2702 ms.
bufferChannelWriteImage| Time to write png: 2869 ms.
filesWriteImage        | Time to write png: 2911 ms.
imageIOWriteImage      | Time to write bmp: 701 ms.
byteArrayWriteImage    | Time to write bmp: 1970 ms.
bufferChannelWriteImage| Time to write bmp: 2029 ms.
filesWriteImage        | Time to write bmp: 1942 ms.
```

## Writing to 500MB RAMDISK [IMDisk - Windows](https://sourceforge.net/projects/imdisk-toolkit/) ##
```
imageIOWriteImage      | Time to write jpg: 1038 ms.
byteArrayWriteImage    | Time to write jpg: 1047 ms.
bufferChannelWriteImage| Time to write jpg: 1042 ms.
filesWriteImage        | Time to write jpg: 1042 ms.
imageIOWriteImage      | Time to write png: 2895 ms.
byteArrayWriteImage    | Time to write png: 2954 ms.
bufferChannelWriteImage| Time to write png: 2919 ms.
filesWriteImage        | Time to write png: 2744 ms.
imageIOWriteImage      | Time to write bmp: 675 ms.
byteArrayWriteImage    | Time to write bmp: 1970 ms.
bufferChannelWriteImage| Time to write bmp: 2035 ms.
filesWriteImage        | Time to write bmp: 1942 ms.
```
