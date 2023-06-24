import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter_todo_app/dashboard.dart';
import 'package:flutter_todo_app/registration.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:velocity_x/velocity_x.dart';
import 'applogo.dart';
import 'package:http/http.dart' as http;
import 'config.dart';

class SignInPage extends StatefulWidget {
  @override
  _SignInPageState createState() => _SignInPageState();
}

class _SignInPageState extends State<SignInPage> {
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  bool _isNotValidate = false;
  late SharedPreferences prefs;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    initSharedPref();
  }

  void initSharedPref() async{
    prefs = await SharedPreferences.getInstance();
  }

  void loginUser() async{
    if(emailController.text.isNotEmpty && passwordController.text.isNotEmpty){

      var response = await http.post(Uri.parse(login),
          headers: {"Content-Type":"application/json",
                    "dsLogin": emailController.text,
                    "dsSenha": passwordController.text}
      );

      var jsonResponse = jsonDecode(response.body);
      if(response.statusCode == 200){
          var myToken = jsonResponse['token'];
          prefs.setString('token', myToken);
          Navigator.push(context, MaterialPageRoute(builder: (context)=>Dashboard(token: myToken)));
      }else{
        print('Algo deu errado!');
      }
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
                  "Email Registro".text.size(22).yellow100.make(),

                  TextField(
                    controller: emailController,
                    keyboardType: TextInputType.text,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Colors.white,
                        hintText: "Usuario",
                        errorText: _isNotValidate ? "Coloque informações validas.." : null,
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                  ).p4().px24(),
                  TextField(
                    controller: passwordController,
                    keyboardType: TextInputType.text,
                    obscureText: true, // Para ocultar o texto digitado como senha
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Colors.white,
                        hintText: "Senha",
                        errorText: _isNotValidate ? "Coloque informações validas.." : null,
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                  ).p4().px24(),
                  GestureDetector(
                    onTap: (){
                        loginUser();
                    },
                    child: HStack([
                      VxBox(child: "Entrar".text.white.makeCentered().p16()).green600.roundedLg.make(),
                    ]),
                  ),
                ],
              ),
            ),
          ),
        ),
        bottomNavigationBar: GestureDetector(
          onTap: (){
            Navigator.push(context, MaterialPageRoute(builder: (context)=>Registration()));
          },
          child: Container(
              height: 25,
              color: Colors.lightBlue,
              child: Center(child: "Crie uma nova conta..! Requistre-se ".text.white.makeCentered())),
        ),
      ),
    );
  }
}