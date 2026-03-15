import 'package:flutter/material.dart';
import 'package:galeria/presentation/components/custom_navbar.dart';
import 'package:galeria/presentation/components/custom_navbar_icon.dart';
import 'package:galeria/presentation/router/folder_router.dart';
import 'package:galeria/presentation/router/library_router.dart';
import 'package:galeria/presentation/router/photo_router.dart';
import 'package:galeria/presentation/router/search_router.dart';
import 'package:galeria/presentation/router/video_router.dart';
import 'package:galeria/presentation/theme/application_colors.dart';

class MainPage extends StatefulWidget {
  const MainPage({super.key});

  @override
  State<MainPage> createState() => _MainPageState();
}

class _MainPageState extends State<MainPage>
    with TickerProviderStateMixin<MainPage> {
  static final _folderRouter = FolderRouter();
  static final _libraryRouter = LibraryRouter();
  static final _photoRouter = PhotoRouter();
  static final _searchRouter = SearchRouter();
  static final _videoRouter = VideoRouter();

  static List<Destination> allDestinations = <Destination>[
    Destination(0, "assets/image.png", _photoRouter.onGenerateRoute),
    Destination(1, "assets/video-play.png", _videoRouter.onGenerateRoute),
    Destination(2, "assets/folder-add.png", _folderRouter.onGenerateRoute),
    Destination(3, "assets/search-normal.png", _searchRouter.onGenerateRoute),
    Destination(4, "assets/library.png", _libraryRouter.onGenerateRoute),
  ];

  late final List<GlobalKey<NavigatorState>> navigatorKeys;
  late final List<GlobalKey> destinationKeys;
  late final List<AnimationController> destinationFaders;
  late final List<Widget> destinationViews;
  int selectedIndex = 0;

  AnimationController buildFaderController() {
    return AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 300),
    )..addStatusListener((AnimationStatus status) {
      if (status.isDismissed) {
        setState(() {}); // Rebuild unselected destinations offstage.
      }
    });
  }

  @override
  void initState() {
    super.initState();

    navigatorKeys = List<GlobalKey<NavigatorState>>.generate(
      allDestinations.length,
      (int index) => GlobalKey(),
    ).toList();

    destinationFaders = List<AnimationController>.generate(
      allDestinations.length,
      (int index) => buildFaderController(),
    ).toList();
    destinationFaders[selectedIndex].value = 1.0;

    final CurveTween tween = CurveTween(curve: Curves.fastOutSlowIn);

    destinationViews = allDestinations.map<Widget>((Destination destination) {
      return FadeTransition(
        opacity: destinationFaders[destination.index].drive(tween),
        child: DestinationView(
          destination: destination,
          navigatorKey: navigatorKeys[destination.index],
        ),
      );
    }).toList();
  }

  @override
  void dispose() {
    for (final AnimationController controller in destinationFaders) {
      controller.dispose();
    }
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return NavigatorPopHandler(
      onPopWithResult: (void result) {
        final NavigatorState navigator =
            navigatorKeys[selectedIndex].currentState!;
        navigator.pop();
      },
      child: Scaffold(
        body: SafeArea(
          top: false,
          child: Stack(
            fit: StackFit.expand,
            children: allDestinations.map((Destination destination) {
              final int index = destination.index;
              final Widget view = destinationViews[index];
              if (index == selectedIndex) {
                destinationFaders[index].forward();
                return Offstage(offstage: false, child: view);
              } else {
                destinationFaders[index].reverse();
                if (destinationFaders[index].isAnimating) {
                  return IgnorePointer(child: view);
                }
                return Offstage(child: view);
              }
            }).toList(),
          ),
        ),
        bottomNavigationBar: CustomNavbar(
          icons: allDestinations.map<Widget>((destination) {
            return CustomNavbarIcon(
              onPressed: () {
                setState(() {
                  selectedIndex = destination.index;
                });
              },
              selected: selectedIndex == destination.index,
              imageName: destination.iconName,
            );
          }).toList(),
          // icons: [
          //   IconButton(
          //     onPressed: () {
          //       setState(() {
          //         selectedIndex = 0;
          //       });
          //     },
          //     icon: CustomNavbarIcon(
          //       imageName: "assets/image.png",
          //       selected: selectedIndex == 0,
          //     ),
          //   ),
          //   IconButton(
          //     onPressed: () {
          //       setState(() {
          //         selectedIndex = 1;
          //       });
          //     },
          //     icon: CustomNavbarIcon(
          //       imageName: "assets/video-play.png",
          //       selected: selectedIndex == 1,
          //     ),
          //   ),
          //   IconButton(
          //     onPressed: () {
          //       setState(() {
          //         selectedIndex = 2;
          //       });
          //     },
          //     icon: CustomNavbarIcon(
          //       imageName: "assets/folder-add.png",
          //       selected: selectedIndex == 2,
          //     ),
          //   ),
          //   IconButton(
          //     onPressed: () {
          //       setState(() {
          //         selectedIndex = 3;
          //       });
          //     },
          //     icon: CustomNavbarIcon(
          //       imageName: "assets/search-normal.png",
          //       selected: selectedIndex == 3,
          //     ),
          //   ),
          //   IconButton(
          //     onPressed: () {
          //       setState(() {
          //         selectedIndex = 4;
          //       });
          //     },
          //     icon: CustomNavbarIcon(
          //       imageName: "assets/library.png",
          //       selected: selectedIndex == 4,
          //     ),
          //   ),
          // ],
        ),
      ),
    );
  }
}

class Destination {
  const Destination(this.index, this.iconName, this.routes);

  final int index;
  final String iconName;
  final Route<dynamic>? Function(RouteSettings)? routes;
}

class DestinationView extends StatefulWidget {
  const DestinationView({
    super.key,
    required this.destination,
    required this.navigatorKey,
  });

  final Destination destination;
  final Key navigatorKey;

  @override
  State<DestinationView> createState() => _DestinationViewState();
}

class _DestinationViewState extends State<DestinationView> {
  @override
  Widget build(BuildContext context) {
    return Navigator(
      key: widget.navigatorKey,
      onGenerateRoute: widget.destination.routes,
    );
  }
}
