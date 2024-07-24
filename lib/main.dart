import 'package:flutter/material.dart';
import 'package:network_info_app/app_notifier.dart';
import 'package:provider/provider.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(
    ChangeNotifierProvider(
      create: (context) => AppNotifier()..initialize(),
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: Consumer<AppNotifier>(
        builder: (context, value, child) => Scaffold(
          body: Center(
            child: ElevatedButton(
              onPressed: () {
                value.networkInfo.getCellInfo();
              },
              child: Text('Get Info'),
            ),
          ),
        ),
      ),
    );
  }
}
