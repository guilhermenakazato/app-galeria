import 'package:flutter/material.dart';
import 'package:galeria/presentation/pages/main_page.dart';
import 'package:galeria/presentation/pages/videos_page.dart';

class VideoRouter {
  Route onGenerateRoute(RouteSettings settings) {
    return MaterialPageRoute(
      settings: settings,
      builder: (context) {
        switch (settings.name) {
          case '/':
            return VideosPage();
        }
        return Center(child: Text("Erro!"));
      },
    );
  }
}