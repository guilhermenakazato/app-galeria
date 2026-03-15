import 'package:flutter/material.dart';
import 'package:galeria/presentation/router/main_router.dart';
import 'package:galeria/presentation/theme/application_theme.dart';

void main() {
  runApp(MainApp(appRouter: MainRouter()));
}

class MainApp extends StatelessWidget {
  final MainRouter appRouter;
  const MainApp({super.key, required this.appRouter});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Galeria",
      onGenerateRoute: appRouter.onGenerateRoute,
      debugShowCheckedModeBanner: false,
      theme: ApplicationTheme.defaultTheme,
    );
  }
}
