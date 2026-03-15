import 'package:flutter/material.dart';
import 'package:galeria/presentation/pages/main_page.dart';

import '../pages/folder_page.dart';

class FolderRouter {
  Route onGenerateRoute(RouteSettings settings) {
    return MaterialPageRoute(
      settings: settings,
      builder: (context) {
        switch (settings.name) {
          case '/':
            return FolderPage();
        }
        return Center(child: Text("Erro!"));
      },
    );
  }
}