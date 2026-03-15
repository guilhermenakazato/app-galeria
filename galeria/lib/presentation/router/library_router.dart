import 'package:flutter/material.dart';
import 'package:galeria/presentation/pages/library_page.dart';

class LibraryRouter {
  Route onGenerateRoute(RouteSettings settings) {
    return MaterialPageRoute(
      settings: settings,
      builder: (context) {
        switch (settings.name) {
          case '/':
            return LibraryPage();
        }
        return Center(child: Text("Erro!"));
      },
    );
  }
}