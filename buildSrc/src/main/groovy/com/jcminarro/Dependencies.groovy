package com.jcminarro

class Dependencies {
    private static String KOTLIN_VERSION = '1.2.0'
    private static String KOTLIN_ARGPARSE_VERSION = '2.0.3'
    private static String JOTA_VERSION = '0.9.6'

    static String kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    static String kotlinSTDLib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:$KOTLIN_VERSION"
    static String argParse = "com.xenomachina:kotlin-argparser:$KOTLIN_ARGPARSE_VERSION"
    static String jota = "com.github.iotaledger:iota~lib~java:0.9.10"
}