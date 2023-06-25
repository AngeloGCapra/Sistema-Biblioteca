import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:velocity_x/velocity_x.dart';
import 'package:http/http.dart' as http;
import 'config.dart';
import 'package:flutter_slidable/flutter_slidable.dart';

class Dashboard extends StatefulWidget {
  final token;
  const Dashboard({@required this.token,Key? key}) : super(key: key);

  @override
  State<Dashboard> createState() => _DashboardState();
}

class _DashboardState extends State<Dashboard> {

  late int userId;

  TextEditingController _login = TextEditingController();
  TextEditingController _senha = TextEditingController();
  TextEditingController _nome = TextEditingController();
  TextEditingController _email = TextEditingController();
  TextEditingController _tipoUsuario = TextEditingController();

  List? items;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    Map<String,dynamic> jwtDecodedToken = JwtDecoder.decode(widget.token);

    if (jwtDecodedToken.containsKey('cdUsuario')) {
      userId = jwtDecodedToken['cdUsuario'] as int;
    } else {
      throw Exception("Claim 'cdUsuario' não encontrada no token JWT.");
    }
    getAllUsuarioList();
  }

  void addTodo() async{
    if(_login.text.isNotEmpty && _senha.text.isNotEmpty){

      var tipoUsuario = {
        "cdTipoUsuario": int.parse(_tipoUsuario.text),
      };

      var reqBody = {
        "dsLogin": _login.text,
        "dsSenha": _senha.text,
        "nmNome": _nome.text,
        "dsEmail": _email.text,
        "tipoUsuario": tipoUsuario,
      };

      var response = await http.post(Uri.parse(addUsuario),
          headers: {"Content-Type":"application/json"},
          body: jsonEncode(reqBody)
      );

      //var jsonResponse = jsonDecode(response.body);

      if(response.statusCode == 201){
        _login.clear();
        _senha.clear();
        _nome.clear();
        _email.clear();
        _tipoUsuario.clear();
        Navigator.pop(context);
        getAllUsuarioList();
      }else{
        print("Algo deu errado!");
      }
    }
  }

  void getAllUsuarioList() async {

    var response = await http.get(Uri.parse(getAllUsuario),
        headers: {"Content-Type":"application/json",
                  "token": widget.token}
    );

    var jsonResponse = jsonDecode(response.body);
    items = jsonResponse;

    setState(() {

    });
  }

  void deleteItem(int id) async{

    var response = await http.delete(Uri.parse(deleteUsuario),
        headers: {"Content-Type":"application/json",
          "cdUsuario": id.toString(),
          "token": widget.token}
    );

    if(response.statusCode == 204){
      getAllUsuarioList();
    }
  }

  void updateItem(int id, TextEditingController loginController, TextEditingController senhaController, TextEditingController nomeController, TextEditingController emailController, TextEditingController tipoUsuarioController) async {
    var tipoUsuario = {
      "cdTipoUsuario": int.parse(tipoUsuarioController.text),
    };

    var requestBody = {
      "cdUsuario": id,
      "dsLogin": loginController.text,
      "dsSenha": senhaController.text,
      "nmNome": nomeController.text,
      "dsEmail": emailController.text,
      "tipoUsuario": tipoUsuario,
    };

    var response = await http.put(
      Uri.parse(editarUsuario),
      headers: {"Content-Type": "application/json",
                "token": widget.token},
        body: jsonEncode(requestBody),
    );

    if (response.statusCode == 204) {
      Navigator.pop(context);
      getAllUsuarioList();
    } else {
      print("Falha na atualização do item");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.lightBlueAccent,
       body: Column(
         crossAxisAlignment: CrossAxisAlignment.start,
         children: [
           Container(
             padding: EdgeInsets.only(top: 60.0,left: 30.0,right: 30.0,bottom: 30.0),
             child: Column(
               crossAxisAlignment: CrossAxisAlignment.start,
               children: [
                 CircleAvatar(child: Icon(Icons.list,size: 30.0,),backgroundColor: Colors.white,radius: 30.0,),
                 SizedBox(height: 10.0),
                 Text('Projeto de Sistema Bibliotecário',style: TextStyle(fontSize: 30.0,fontWeight: FontWeight.w700),),
                 SizedBox(height: 8.0),
               ],
             ),
           ),
           Expanded(
             child: Container(
               decoration: BoxDecoration(
                   color: Colors.white,
                   borderRadius: BorderRadius.only(topLeft: Radius.circular(20),topRight: Radius.circular(20))
               ),
               child: Padding(
                 padding: const EdgeInsets.all(8.0),
                 child: items == null ? null : ListView.builder(
                     itemCount: items!.length,
                     itemBuilder: (context,int index){
                       return Slidable(
                         key: const ValueKey(0),
                         endActionPane: ActionPane(
                           motion: const ScrollMotion(),
                           dismissible: DismissiblePane(onDismissed: () {}),
                           children: [
                             SlidableAction(
                               backgroundColor: Color(0xFFFE4A49),
                               foregroundColor: Colors.white,
                               icon: Icons.delete,
                               label: 'Excluir',
                               onPressed: (BuildContext context) {
                                 var id = items![index]['cdUsuario']; // Obtenha o ID do item do card
                                 if (id != null) {
                                   deleteItem(id); // Chama a função deleteItem passando o ID
                                 } else {
                                   print("cdUsuario nulo");
                                 }
                               },
                             ),
                           ],
                         ),
                         child: Card(
                           borderOnForeground: false,
                           child: ListTile(
                             leading: GestureDetector(
                               onTap: () =>_displayTextEditDialog(context, items![index]['cdUsuario'], items![index]),
                               child: Icon(Icons.edit),
                             ),
                             title: Text('${items![index]['dsLogin']}'),
                             subtitle: Text('${items![index]['nmNome']}'),
                             trailing: Icon(Icons.arrow_back),
                           ),
                         ),
                       );
                     }
                 ),
               ),
             ),
           )
         ],
       ),
      floatingActionButton: FloatingActionButton(
        onPressed: () =>_displayTextInputDialog(context),
        child: Icon(Icons.add),
        tooltip: 'Adicionar',
      ),
    );
  }

  Future<void> _displayTextInputDialog(BuildContext context) async {
    return showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Adicionar'),
            content: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextField(
                  controller: _login,
                  keyboardType: TextInputType.text,
                  decoration: InputDecoration(
                      filled: true,
                      fillColor: Colors.white,
                      hintText: "Login",
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                ).p4().px8(),
                TextField(
                  controller: _senha,
                  keyboardType: TextInputType.text,
                  decoration: InputDecoration(
                      filled: true,
                      fillColor: Colors.white,
                      hintText: "Senha",
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                ).p4().px8(),
                TextField(
                  controller: _nome,
                  keyboardType: TextInputType.text,
                  decoration: InputDecoration(
                      filled: true,
                      fillColor: Colors.white,
                      hintText: "Nome",
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                ).p4().px8(),
                TextField(
                  controller: _email,
                  keyboardType: TextInputType.text,
                  decoration: InputDecoration(
                      filled: true,
                      fillColor: Colors.white,
                      hintText: "Email",
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                ).p4().px8(),
                TextField(
                  controller: _tipoUsuario,
                  keyboardType: TextInputType.text,
                  decoration: InputDecoration(
                      filled: true,
                      fillColor: Colors.white,
                      hintText: "Tipo de Usuário (Código)",
                      border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(Radius.circular(10.0)))),
                ).p4().px8(),
                ElevatedButton(onPressed: (){
                  addTodo();
                  }, child: Text("Adicionar"))
              ],
            )
          );
        });
  }

  Future<void> _displayTextEditDialog(BuildContext context, int id, Map<String, dynamic> item) async {
    TextEditingController _loginEdit = TextEditingController(text: item['dsLogin']);
    TextEditingController _senhaEdit = TextEditingController(text: item['dsSenha']);
    TextEditingController _nomeEdit = TextEditingController(text: item['nmNome']);
    TextEditingController _emailEdit = TextEditingController(text: item['dsEmail']);
    TextEditingController _tipoUsuarioEdit = TextEditingController(text: item['tipoUsuario']['cdTipoUsuario'].toString());

    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text('Editar'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: _loginEdit,
                keyboardType: TextInputType.text,
                decoration: InputDecoration(
                  filled: true,
                  fillColor: Colors.white,
                  hintText: "Login",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10.0)),
                  ),
                ),
              ).p4().px8(),
              TextField(
                controller: _senhaEdit,
                keyboardType: TextInputType.text,
                decoration: InputDecoration(
                  filled: true,
                  fillColor: Colors.white,
                  hintText: "Senha",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10.0)),
                  ),
                ),
              ).p4().px8(),
              TextField(
                controller: _nomeEdit,
                keyboardType: TextInputType.text,
                decoration: InputDecoration(
                  filled: true,
                  fillColor: Colors.white,
                  hintText: "Nome",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10.0)),
                  ),
                ),
              ).p4().px8(),
              TextField(
                controller: _emailEdit,
                keyboardType: TextInputType.text,
                decoration: InputDecoration(
                  filled: true,
                  fillColor: Colors.white,
                  hintText: "Email",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10.0)),
                  ),
                ),
              ).p4().px8(),
              TextField(
                controller: _tipoUsuarioEdit,
                keyboardType: TextInputType.text,
                decoration: InputDecoration(
                  filled: true,
                  fillColor: Colors.white,
                  hintText: "Tipo Usuario",
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10.0)),
                  ),
                ),
              ).p4().px8(),
              ElevatedButton(
                onPressed: () {
                  updateItem(
                    id, _loginEdit, _senhaEdit, _nomeEdit, _emailEdit, _tipoUsuarioEdit);
                },
                child: Text("Atualizar"),
              ),
            ],
          ),
        );
      },
    );
  }
}

