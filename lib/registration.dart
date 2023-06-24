import 'dart:convert';
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:velocity_x/velocity_x.dart';
import 'applogo.dart';
import 'loginPage.dart';
import 'package:http/http.dart' as http;
import 'config.dart';

class Registration extends StatefulWidget {
  @override
  _RegistrationState createState() => _RegistrationState();
}

class _RegistrationState extends State<Registration> {

  TextEditingController loginController = TextEditingController();
  TextEditingController senhaController = TextEditingController();
  TextEditingController nomeController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController tipoUsuarioController = TextEditingController();

  bool _isNotValidate = false;

  void registerUser() async {
    if (loginController.text.isNotEmpty && senhaController.text.isNotEmpty) {
      var tipoUsuario = {
        "cdTipoUsuario": int.parse(tipoUsuarioController.text),
      };

      var reqBody = {
        "dsLogin": loginController.text,
        "dsSenha": senhaController.text,
        "nmNome": nomeController.text,
        "dsEmail": emailController.text,
        "tipoUsuario": tipoUsuario,
      };

      var response = await http.post(Uri.parse(registration),
        headers: {"Content-Type": "application/json"},
        body: jsonEncode(reqBody),
      );

      var jsonResponse = jsonDecode(response.body);

      if (response.statusCode == 201) {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => SignInPage()),
        );
      } else {
        print("Algo deu errado!");
      }
    } else {
      setState(() {
        _isNotValidate = true;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height,
          decoration: BoxDecoration(
            gradient: LinearGradient(
                colors: [const Color(0XFFF95A3B),const Color(0XFFF96713)],
                begin: FractionalOffset.topLeft,
                end: FractionalOffset.bottomCenter,
                stops: [0.0,0.8],
                tileMode: TileMode.mirror
            ),
          ),
          child: Center(
            child: SingleChildScrollView(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  CommonLogo(),
                  HeightBox(10),
                  "CRIAR SUA CONTA".text.size(22).yellow100.make(),
                  TextField(
                    controller: loginController,
                    keyboardType: TextInputType.text,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Colors.white,
                        errorStyle: TextStyle(color: Colors.white),
                        errorText: _isNotValidate ? "Algo deu errado, insira os dados corretamente!" : null,
                        hintText: "Login",
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                  ).p4().px24(),
                  TextField(
                    controller: senhaController,
                    keyboardType: TextInputType.text,
                    obscureText: true, // Para ocultar o texto digitado como senha
                    decoration: InputDecoration(
                      suffixIcon: IconButton(
                        icon: Icon(Icons.copy),
                        onPressed: () {
                          final data = ClipboardData(text: senhaController.text);
                          Clipboard.setData(data);
                        },
                      ),
                      filled: true,
                      fillColor: Colors.white,
                      errorStyle: TextStyle(color: Colors.white),
                      errorText:
                      _isNotValidate ? "Algo deu errado, insira os dados corretamente!" : null,
                      hintText: "Senha",
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(10.0)),
                      ),
                    ),
                  ).p4().px24(),
                  TextField(
                    controller: nomeController,
                    keyboardType: TextInputType.text,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Colors.white,
                        errorStyle: TextStyle(color: Colors.white),
                        errorText: _isNotValidate ? "Algo deu errado, insira os dados corretamente!" : null,
                        hintText: "Nome",
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                  ).p4().px24(),
                  TextField(
                    controller: emailController,
                    keyboardType: TextInputType.text,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Colors.white,
                        errorStyle: TextStyle(color: Colors.white),
                        errorText: _isNotValidate ? "Algo deu errado, insira os dados corretamente!" : null,
                        hintText: "Email",
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                  ).p4().px24(),
                  TextField(
                    controller: tipoUsuarioController,
                    keyboardType: TextInputType.text,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Colors.white,
                        errorStyle: TextStyle(color: Colors.white),
                        errorText: _isNotValidate ? "Algo deu errado, insira os dados corretamente!" : null,
                        hintText: "Tipo do Usuário (Código)",
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                  ).p4().px24(),
                  HStack([
                    GestureDetector(
                      onTap: ()=>{
                        registerUser()
                      },
                        child: VxBox(child: "Registrar".text.white.makeCentered().p16()).green600.roundedLg.make().px16().py16(),
                    ),
                  ]),
                  GestureDetector(
                    onTap: (){
                      print("Registrar-se");
                      Navigator.push(context, MaterialPageRoute(builder: (context)=>SignInPage()));
                    },
                    child: HStack([
                      "Já Cadastrado?".text.make(),
                      " Faça Login!".text.white.make()
                    ]).centered(),
                  )
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}