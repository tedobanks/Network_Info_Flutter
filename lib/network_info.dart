import 'dart:async';
import 'package:flutter/services.dart';

class NetworkInfo {
  static const platform = MethodChannel('com.example/network_info');

  Future getCellInfo() async {
    try {
      final Map<dynamic, dynamic> result =
          await platform.invokeMethod('getCellInfo');
      print('result: ${result}');
      return result;
    } catch (e) {
      print("Error: $e");
      return {};
    }
  }
}
