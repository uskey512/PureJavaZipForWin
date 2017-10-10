# PureJavaZipForWin
追加パッケージ無しでWindows向けzipを作成します  

## なぜつくった
`Java Zip 日本語 文字化け`などで調べると  
`org.apache.tools.zip`入れると出来るよって記事がほとんどだったので  
java配下のパッケージのみで実装できるか試したのでそのメモ  

Java7からZipOutputStreamのコンストラクタでcharsetを  
指定出来るものがあるのでそれを使えばOK  
