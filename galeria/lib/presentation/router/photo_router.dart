import 'package:flutter/material.dart';
import 'package:galeria/presentation/pages/photos_page.dart';

class PhotoRouter {
  Route onGenerateRoute(RouteSettings settings) {
    return MaterialPageRoute(
      settings: settings,
      builder: (context) {
        switch (settings.name) {
          case '/':
            return PhotosPage();
        }
        return Center(child: Text("Erro!"));
      },
    );
  }
}