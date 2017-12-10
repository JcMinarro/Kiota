package com.jcminarro

import com.google.gson.Gson
import com.xenomachina.argparser.ArgParser
import jota.IotaAPI

fun main(args: Array<String>) {
    println(KiotaCommander(KiotaArgs(ArgParser(args)).command).run())
}

class KiotaArgs(parse: ArgParser) {

    private val addresses by parse.adding("-a", help = "Address Direcction")

    private val bundles by parse.adding("-b", help = "Bundle")

    private val tags by parse.adding("-t", help = "Tag")

    val command by parse.mapping(
            "--nodeInfo" to GetNodeInfoCommand,
            "-i" to GetNodeInfoCommand,
            "--findByAddress" to FindByAddressCommand({addresses.toTypedArray()}),
            "--findByBundle" to FindByBundleCommand({bundles.toTypedArray()}),
            "--findByTag" to FindByTagCommand({tags.toTypedArray()}),
            help = "Command")
}

class KiotaCommander(private val command: KiotaCommand) {

    private val iotaApi: IotaAPI =
            IotaAPI.Builder()
                    .protocol("http")
                    .host("eugene.iotasupport.com")
                    .port("14999")
                    .build()


    fun run(): String = when (command) {
        GetNodeInfoCommand -> Gson().toJson(iotaApi.nodeInfo)
        is FindByAddressCommand -> Gson().toJson(iotaApi.findTransactionObjectsByAddresses(command.addresses))
        is FindByBundleCommand -> Gson().toJson(iotaApi.findTransactionObjectsByBundle(command.bundles))
        is FindByTagCommand -> Gson().toJson(iotaApi.findTransactionObjectsByTag(command.tags))
    }
}

sealed class KiotaCommand
object GetNodeInfoCommand : KiotaCommand()
data class FindByAddressCommand(private val getAddressesFunction: () -> Array<String>) : KiotaCommand(){
    val addresses
        get() = getAddressesFunction()
}
data class FindByBundleCommand(private val getBundlesFunction: () -> Array<String>) : KiotaCommand(){
    val bundles
        get() = getBundlesFunction()
}
data class FindByTagCommand(private val getTagsFunction: () -> Array<String>) : KiotaCommand(){
    val tags
        get() = getTagsFunction()
}
