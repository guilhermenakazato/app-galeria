import 'package:flutter/cupertino.dart';

import '../theme/application_colors.dart';

class CustomNavbar extends StatelessWidget {
  final List<Widget> icons;
  const CustomNavbar({super.key, required this.icons});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        Container(
          height: 2,
          decoration: BoxDecoration(
            gradient: LinearGradient(colors: [
              Color(0xff191919),
              Color(0xff525252),
              Color(0xff939393),
              Color(0xffdadada),
              Color(0xffdadada),
              Color(0xff939393),
              Color(0xff525353),
              Color(0xff191a1a),
            ]),
          ),
        ),
        Container(
          decoration: BoxDecoration(
            color: ApplicationColors.bottomBarColor,
          ),
          height: MediaQuery.sizeOf(context).height * 0.1,
          padding: EdgeInsets.only(
            left: 16,
            right: 16,
            top: 10,
            bottom: MediaQuery.paddingOf(context).bottom,
          ),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: icons,
          ),
        ),
      ],
    );
  }
}