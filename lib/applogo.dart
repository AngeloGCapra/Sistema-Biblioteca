import 'package:flutter/material.dart';
import 'package:velocity_x/velocity_x.dart';

class CommonLogo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Image.network(
          "https://i.ibb.co/P9jcLWM/png-transparent-computer-icons-library-biblioteca-angle-text-rectangle-clipdrop-background-removal.png",
          width: 300,
        ),
        "BiblioTech".text.xl4.bold.italic.make(),
        "Sistema Bibliotecário".text.light.white.wider.lg.make(),
      ],
    );
  }
}
