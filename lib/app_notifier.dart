import 'package:carrier_info/carrier_info.dart';
import 'package:connectivity_plus/connectivity_plus.dart';
import 'package:flutter/material.dart';
import 'package:telephony/telephony.dart';

import 'network_info.dart';

class AppNotifier extends ChangeNotifier {
  String slot = "";
  String tac = "";
  String lac = "";
  String cellIdentity = "";
  String cellId = "";
  String signalLevel = "";
  String asuLevel = "";
  String arfcn = "";
  String bfic = "";
  String psc = "";
  String bitrateError = "";
  String timingAdvance = "";
  final telephonyManager = Telephony.instance;
  NetworkInfo networkInfo = NetworkInfo();

  void getMcc() async {
    // Get the network operator.
    final networkOperator = await telephonyManager.networkOperator;
    print(networkOperator);
    final mcc = networkOperator?.substring(0, 3);
    final mnc = networkOperator?.substring(3, networkOperator.length);
    print(mcc);
    print(mnc);
  }

  void getCarrier() async {
    final carriername = await telephonyManager.networkOperatorName;
    print(carriername);
  }

  void getNetworkType() async {
    final networkType = await telephonyManager.dataNetworkType;
    print(networkType);
  }

  void getSignalStrength() async {
    final strength = await telephonyManager.signalStrengths;
    print(strength);
  }

  void getCarrierInfo() async {
    AndroidCarrierData? carrierInfo = await CarrierInfo.getAndroidInfo();
    print(carrierInfo);
  }

  void getAllCellInfo() async {
    Map<String, dynamic> cellInfo = await networkInfo.getCellInfo();
    print(cellInfo);
  }

  void initialize() async {
    getMcc();
    getCarrier();
    getNetworkType();
    getSignalStrength();
  }
}
