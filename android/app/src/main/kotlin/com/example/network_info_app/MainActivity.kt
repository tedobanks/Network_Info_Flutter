package com.example.network_info_app

import android.annotation.SuppressLint
import android.telephony.CellInfoCdma
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoNr
import android.telephony.CellInfoWcdma
import android.telephony.TelephonyManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel



class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example/network_info"

    private lateinit var channel: MethodChannel

    @SuppressLint("MissingPermission")
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        
        channel.setMethodCallHandler { call, result ->

            if (call.method ==  "getCellInfo") {

                //PING TIME
                var time = ""
                //
                //PING ACTION
                val command = "ping -c 1 google.com"
                val process = Runtime.getRuntime().exec(command)
                process.waitFor()
                val output = process.inputStream.bufferedReader().readText()
                val regex = Regex("time=(\\d+) ms")
                val match = regex.find(output)
                if (match != null) {
                    time = match.groupValues[1] // assign the global variable
                    println("$time ms") // print the time
                } else {
                    println("No time found")
                }
                //

                fun getNetworkTypeName(networkType: Int): String {
                    when (networkType) {
                        TelephonyManager.NETWORK_TYPE_UNKNOWN -> return "UNKNOWN"
                        TelephonyManager.NETWORK_TYPE_GPRS -> return "GPRS"
                        TelephonyManager.NETWORK_TYPE_EDGE -> return "EDGE"
                        TelephonyManager.NETWORK_TYPE_UMTS -> return "UMTS"
                        TelephonyManager.NETWORK_TYPE_HSDPA -> return "HSDPA"
                        TelephonyManager.NETWORK_TYPE_HSUPA -> return "HSUPA"
                        TelephonyManager.NETWORK_TYPE_HSPA -> return "HSPA"
                        TelephonyManager.NETWORK_TYPE_CDMA -> return "CDMA"
                        TelephonyManager.NETWORK_TYPE_EVDO_0 -> return "EVDO_0"
                        TelephonyManager.NETWORK_TYPE_EVDO_A -> return "EVDO_A"
                        TelephonyManager.NETWORK_TYPE_EVDO_B -> return "EVDO_B"
                        TelephonyManager.NETWORK_TYPE_1xRTT -> return "1xRTT"
                        TelephonyManager.NETWORK_TYPE_IDEN -> return "IDEN"
                        TelephonyManager.NETWORK_TYPE_LTE -> return "LTE"
                        TelephonyManager.NETWORK_TYPE_EHRPD -> return "EHRPD"
                        TelephonyManager.NETWORK_TYPE_HSPAP -> return "HSPAP"
                        TelephonyManager.NETWORK_TYPE_NR -> return "NR"
                        else -> return "Unknown"
                    }
                }

                //CELL INFO
                val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                val dataNetworkType = getNetworkTypeName(telephonyManager.dataNetworkType)
                val cellInfo = telephonyManager.allCellInfo[0]
                val cellIdentity = cellInfo.cellIdentity
                val cellSignalStrength = cellInfo.cellSignalStrength
                //

                val cellInfoMap = hashMapOf<String, Any>()
                if (cellInfo != null) {

                    cellInfoMap["dbm"] = cellInfo.cellSignalStrength.dbm
                    cellInfoMap["asulevel"] = cellInfo.cellSignalStrength.asuLevel
                    cellInfoMap["level"] = cellInfo.cellSignalStrength.level
                    cellInfoMap["dataNetworkType"] = dataNetworkType

                }
                //For GSM only
                if (cellInfo is CellInfoGsm) {
                    cellInfoMap["ping"] = time+"ms"
                    cellInfoMap["isRegistered"] = cellInfo.isRegistered
                    cellInfoMap["timestampMilli"] = cellInfo.timestampMillis
                    cellInfoMap["dataNetworkType"] = dataNetworkType
                    cellInfoMap["arfcn"] = cellInfo.cellIdentity.arfcn
                    cellInfoMap["cid"] = cellInfo.cellIdentity.cid
                    cellInfoMap["plmn"] = "${cellInfo.cellIdentity.mccString}${cellInfo.cellIdentity.mncString}"
                    cellInfoMap["mnc"] = cellInfo.cellIdentity.mncString.toString()
                    cellInfoMap["mcc"] = cellInfo.cellIdentity.mccString.toString()
                    cellInfoMap["networkOperator"] = cellInfo.cellIdentity.mobileNetworkOperator.toString()
                    cellInfoMap["lac"] = cellInfo.cellIdentity.lac
                    cellInfoMap["bsic"] = cellInfo.cellIdentity.bsic
                    cellInfoMap["timingAdvance"] = cellInfo.cellSignalStrength.timingAdvance
                    cellInfoMap["bitErrorRate"] = cellInfo.cellSignalStrength.bitErrorRate
                    cellInfoMap["dbm"] = cellInfo.cellSignalStrength.dbm
                    cellInfoMap["level"] = cellInfo.cellSignalStrength.level
                    cellInfoMap["asuLevel"] = cellInfo.cellSignalStrength.asuLevel
                    cellInfoMap["rssi"] = cellInfo.cellSignalStrength.rssi
                }

                //For CDMA only
                if (cellInfo is CellInfoCdma) {
                    cellInfoMap["ping"] = time+"ms"
                    cellInfoMap["isRegistered"] = cellInfo.isRegistered
                    cellInfoMap["timestampMilli"] = cellInfo.timestampMillis
                    cellInfoMap["dataNetworkType"] = dataNetworkType
                    cellInfoMap["networkID"] = cellInfo.cellIdentity.networkId
                    cellInfoMap["baseStationId"] = cellInfo.cellIdentity.basestationId
                    cellInfoMap["longitude"] = cellInfo.cellIdentity.longitude
                    cellInfoMap["latitude"] = cellInfo.cellIdentity.latitude
                    cellInfoMap["systemID"] = cellInfo.cellIdentity.systemId
                    cellInfoMap["level"] = cellInfo.cellSignalStrength.level
                    cellInfoMap["asuLevel"] = cellInfo.cellSignalStrength.asuLevel
                    cellInfoMap["dbm"] = cellInfo.cellSignalStrength.dbm
                    cellInfoMap["cdmaDbm"] = cellInfo.cellSignalStrength.cdmaDbm
                    cellInfoMap["cdmaLevel"] = cellInfo.cellSignalStrength.cdmaLevel
                    cellInfoMap["cdmaEcio"] = cellInfo.cellSignalStrength.cdmaEcio
                    cellInfoMap["evdoDbm"] = cellInfo.cellSignalStrength.evdoDbm
                    cellInfoMap["evdoLevel"] = cellInfo.cellSignalStrength.evdoLevel
                    cellInfoMap["evdoSnr"] = cellInfo.cellSignalStrength.evdoSnr

                }

                //For WCDMA only
                if (cellInfo is CellInfoWcdma) {
                    cellInfoMap["ping"] = time+"ms"
                    cellInfoMap["isRegistered"] = cellInfo.isRegistered
                    cellInfoMap["timestampMilli"] = cellInfo.timestampMillis
                    cellInfoMap["dataNetworkType"] = dataNetworkType
                    cellInfoMap["cid"] = cellInfo.cellIdentity.cid
                    cellInfoMap["lac"] = cellInfo.cellIdentity.lac
                    cellInfoMap["psc"] = cellInfo.cellIdentity.psc
                    cellInfoMap["plmn"] = "${cellInfo.cellIdentity.mccString}${cellInfo.cellIdentity.mncString}"
                    cellInfoMap["mcc"] = cellInfo.cellIdentity.mccString.toString()
                    cellInfoMap["mnc"] = cellInfo.cellIdentity.mncString.toString()
                    cellInfoMap["mobileNetworkOperator"] = cellInfo.cellIdentity.mobileNetworkOperator.toString()
                    cellInfoMap["uarfcn"] = cellInfo.cellIdentity.uarfcn
                    cellInfoMap["level"] = cellInfo.cellSignalStrength.level
                    cellInfoMap["asulevel"] = cellInfo.cellSignalStrength.asuLevel
                    cellInfoMap["ecNo"] = cellInfo.cellSignalStrength.ecNo
                    cellInfoMap["dbm"] = cellInfo.cellSignalStrength.dbm
                }


                // For LTE only
                if (cellInfo is CellInfoLte) {
                    cellInfoMap["ping"] = time+"ms"
                    cellInfoMap["isRegistered"] = cellInfo.isRegistered
                    cellInfoMap["timestampMilli"] = cellInfo.timestampMillis
                    cellInfoMap["dataNetworkType"] = dataNetworkType
                    cellInfoMap["plmn"] = "${cellInfo.cellIdentity.mccString}${cellInfo.cellIdentity.mncString}"
                    cellInfoMap["mcc"] = cellInfo.cellIdentity.mccString.toString()
                    cellInfoMap["mnc"] = cellInfo.cellIdentity.mncString.toString()
                    cellInfoMap["mobileNetworkOperator"] = telephonyManager.networkOperatorName
                    cellInfoMap["bands"] = cellInfo.cellIdentity.bands
                    cellInfoMap["tac"] = cellInfo.cellIdentity.tac
                    cellInfoMap["pci"] = cellInfo.cellIdentity.pci
                    cellInfoMap["ci"] = cellInfo.cellIdentity.ci
                    cellInfoMap["bandwidth"] = cellInfo.cellIdentity.bandwidth
                    cellInfoMap["tac"] = cellInfo.cellIdentity.earfcn
                    cellInfoMap["timingadvance"] = cellInfo.cellSignalStrength.timingAdvance
                    cellInfoMap["cqi"] = cellInfo.cellSignalStrength.cqi
                    cellInfoMap["asulevel"] = cellInfo.cellSignalStrength.asuLevel
                    cellInfoMap["rsrp"] = cellInfo.cellSignalStrength.rsrp
                    cellInfoMap["rsrq"] = cellInfo.cellSignalStrength.rsrq
                    cellInfoMap["rssi"] = cellInfo.cellSignalStrength.rssi
                    cellInfoMap["rssnr"] = cellInfo.cellSignalStrength.rssnr
                    cellInfoMap["dbm"] = cellInfo.cellSignalStrength.dbm
                    cellInfoMap["level"] = cellInfo.cellSignalStrength.level
                }

                // For NR only
                if (cellInfo is CellInfoNr) {
                    cellInfoMap["ping"] = time+"ms"
                    cellInfoMap["connectionStatus"] = cellInfo.cellConnectionStatus
                    cellInfoMap["isRegistered"] = cellInfo.isRegistered
                    cellInfoMap["dataNetworkType"] = dataNetworkType
                    cellInfoMap["dbm"] = cellInfo.cellSignalStrength.dbm
                    cellInfoMap["tac"] = cellInfo.cellSignalStrength.level
                    cellInfoMap["asuLevel"] = cellInfo.cellSignalStrength.asuLevel
                }

                result.success(cellInfoMap)
            }
        }
    }
}
