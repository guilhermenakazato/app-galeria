import 'package:flutter/material.dart';
import 'package:galeria/presentation/pages/search_page.dart';

class SearchRouter {
  Route onGenerateRoute(RouteSettings settings) {
    return MaterialPageRoute(
      settings: settings,
      builder: (context) {
        switch (settings.name) {
          case '/':
            return SearchPage();
        }
        return Center(child: Text("Erro!"));
      },
    );
  }
}