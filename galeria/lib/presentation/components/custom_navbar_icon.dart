import 'package:flutter/material.dart';
import 'package:galeria/presentation/theme/application_colors.dart';

class CustomNavbarIcon extends StatelessWidget {
  final void Function()? onPressed;
  final bool selected;
  final String imageName;
  const CustomNavbarIcon({super.key, required this.onPressed, required this.selected, required this.imageName});

  @override
  Widget build(BuildContext context) {
    return IconButton(
      onPressed: onPressed,
      icon: CustomIcon(
        imageName: imageName,
        selected: selected,
      ),
    );
  }
}

class CustomIcon extends StatelessWidget {
  final String imageName;
  final bool selected;
  const CustomIcon({
    super.key,
    required this.imageName,
    required this.selected,
  });

  @override
  Widget build(BuildContext context) {
    if (selected) {
      return Container(
        width: 40,
        height: 40,
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(10),
        ),
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Image.asset(imageName, color: ApplicationColors.bottomBarColor),
        ),
      );
    }

    return Image.asset(imageName);
  }
}