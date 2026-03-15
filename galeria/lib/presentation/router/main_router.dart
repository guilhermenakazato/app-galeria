import 'package:flutter/material.dart';
import 'package:galeria/presentation/pages/main_page.dart';

class MainRouter {
  Route onGenerateRoute(RouteSettings settings) {
    return MaterialPageRoute(
      settings: settings,
      builder: (context) {
        switch (settings.name) {
          case '/':
            return MainPage();
        }
        return Center(child: Text("Erro!"));
      },
    );
  }
}