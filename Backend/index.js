

var uuid = require ('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

//connect to mysql

var con = mysql.createConnection({


/*host :'bipfvg7zsf3v80fqv2vn-mysql.services.clever-cloud.com',
database :'bipfvg7zsf3v80fqv2vn',
user :'uk26ygbfhswavk2w',
password :'a3IO40oMwxt5N2DwVeH8',
JWT_KEY :'771b0c060c564f5ab24e12e3e8821e04f75ca4bf79159d95a6924faa0004f2216968d895ebb1a8fdbafb0242ba75c4f843a59a19c41ff09accb2432f98fb0d9c',
JWT_REFRESH_KEY :'2dffd66eef911c20a20c0547ded56925fbbc704ebb000cf96107e63f0c018dc11554f2cec12f2ac5ec14854603ed60aa1eed99dfb0864880425f28078d19a382',
MAIL_SENDER :'cojes49808@ffeast.com'*/


    host : 'localhost',
    user : 'root',
    password : '',
    database:'med'

   /* host : 'bipfvg7zsf3v80fqv2vn-mysql.services.clever-cloud.com',
    user : 'uk26ygbfhswavk2w',
    password : 'a3IO40oMwxt5N2DwVeH8',
    database:'bipfvg7zsf3v80fqv2vn'   */

});

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


app.post('/register/',(req , res , next)=>{

    var post_data = req.body;
    var uid = uuid.v4();
    var password = post_data.password;

    var firstname = post_data.firstname;
    var lastname = post_data.lastname;
    var email = post_data.email;

    con.query('SELECT * from Users where email=?',[email],function(err,result,fields){
            con.on('error',function(err){
                console.log('[MySQL ERROR]',err);
            });
            if(result && result.length)
            res.json('User Already Exists !');
            else{
                con.query('INSERT INTO `Users`(`id`, `firstname`,`lastname`, `email`, `password`) VALUES (?,?,?,?,?)'
                ,[uid,firstname,lastname,email,password], function(err, result, fields){

                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                        res.json('Register error : ', err);
                    });
                    res.end(JSON.stringify('Register successfully'));
                })
            }
    });

})

app.post('/login/',(req,res,next)=>{

    var post_data = req.body;
    var user_password = post_data.password;
    var email =  post_data.email;

    con.query('select * from Users where email=?', [email],function(err,result,fields){

        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
                if(result && result.length){
                    var password = result[0].password;

                    if(password == user_password)
                        res.end(JSON.stringify(result[0]));
                    else res.end(JSON.stringify('Wrong password'));

                }else {res.end(JSON.stringify('User Not exists !!'));}

    });

})
app.post('/card/',(req,res,next)=>{

    var post_data = req.body;
    var idCli = post_data.idcli;

    con.query('select * from Addcard where IdCli=?',[idCli],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
                if(result && result.length){
                    res.json(result);
                }else {res.end('noproducts');}

    });

})
app.post('/order/',(req,res,next)=>{

    var post_data = req.body;
    var idCli = post_data.idcli;

    con.query('select * from Addcard where IdCli=?',[idCli],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
                if(result && result.length){
                    res.json(result);
                }else {res.end('noproducts');}

    });

})


app.post('/contact/',(req , res , next)=>{

    var post_data = req.body;
    var user_name = post_data.name;
    var user_email =  post_data.email;
    var phone = post_data.phone;
    var description = post_data.description;

    con.query('INSERT INTO `Contact`(`name`, `email`,`phone`, `description`) VALUES (?,?,?,?)'
    ,[user_name,user_email,phone,description], function(err, result, fields){

        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
            res.json('Contact error : ', err);
        });
        res.json('Send it successfully');
    });


})



app.post('/home/',(req,res,next)=>{

    var post_data = req.body;

    con.query('select * from Products ',function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
                if(result && result.length){
                    res.json(result);
                }else {res.end(JSON.stringify('no products'));}

    });

})

app.post('/profile/',(req,res,next)=>{

    var post_data = req.body;
    var user_id = post_data.id;

    con.query('select * from Users where id=? ',[user_id],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
                if(result && result.length){
                    res.json(result);
                }else {res.end(JSON.stringify('no user'));}

    });

})
app.post('/del/',(req,res,next)=>{

    var post_data = req.body;
    var idcli = post_data.idcli;

    con.query('DELETE from Addcard where IdCli=? ',[idcli],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
                if(result && result.length){
                    res.end(JSON.stringify('success'));
                }else {res.end(JSON.stringify('success'));}

    });

})
app.post('/delone/',(req,res,next)=>{

    var post_data = req.body;
    var idcard = post_data.idcard;

    con.query('DELETE from Addcard where IdCard=? ',[idcard],function(err,result,fields){
        con.on('error',function(err){
            console.log('[MySQL ERROR]',err);
        });
                if(result && result.length){
                    res.end(JSON.stringify('success'));
                }else {res.end(JSON.stringify('success'));}

    });

})

app.post('/addcard/',(req , res , next)=>{

    var post_data = req.body;
    var uid = uuid.v4();

    var IdCli = post_data.IdCli;
    var idProd = post_data.idProd;
    var title = post_data.title;
    var description = post_data.description;
    var price = post_data.price;
    var imglink = post_data.imglink;
    var qte = post_data.qte;


    con.query('INSERT INTO `Addcard`(`IdCard`, `IdCli`,`idProd`, `title`, `description`,`price`,`imglink`,`qte`) VALUES (?,?,?,?,?,?,?,?)'
                ,[uid,IdCli,idProd,title,description,price,imglink,qte], function(err, result, fields){
                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                        res.json('Register error : ', err);
                    });
                    res.end(JSON.stringify('Product Added successfully'));
                });
            })

app.post('/adadr/',(req , res , next)=>{

                var post_data = req.body;
                var id = post_data.id;
                var adr =  post_data.adrs;

                con.query('UPDATE `Users` SET adresse=? where id=? '
                ,[adr,id], function(err, result, fields){

                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                        res.json('Contact error : ', err);
                    });
                    res.json(JSON.stringify('Send it successfully'));
                });

})


app.post('/insorders/',(req , res , next)=>{

    var post_data = req.body;
    var uid = uuid.v4();

    var IdCli = post_data.IdCli;
    var idProd = post_data.idProd;
    var title = post_data.title;
    var description = post_data.description;
    var price = post_data.price;
    var imglink = post_data.imglink;
    var qte = post_data.qte;
    var confir = post_data.confirmation;
    var adress = post_data.adress;

    con.query('INSERT INTO `Orders`(`IdOrder`, `IdCli`,`idProd`, `title`, `description`,`price`,`imglink`,`qte` , `confirmation` ,`adress`) VALUES (?,?,?,?,?,?,?,?,?,?)'
                ,[uid,IdCli,idProd,title,description,price,imglink,qte,confir,adress], function(err, result, fields){
                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                        res.json('Register error : ', err);
                    });
                    res.end(JSON.stringify('successfully'));
                });
            })


app.post('/updpass/',(req,res,next)=>{

                var post_data = req.body;
                var id =  post_data.id;
                var opass= post_data.opass;
                var npass= post_data.npass;

                con.query('select * from Users where id=?', [id],function(err,result,fields){

                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                    });
                            if(result && result.length){
                                var password = result[0].password;

                                if(password == opass){

                                    con.query('UPDATE `Users` SET password=? where id=? '
                                    ,[npass,id], function(err, result, fields){

                                        con.on('error',function(err){
                                            console.log('[MySQL ERROR]',err);
                                        });
                                    });
                                    res.end(JSON.stringify(result[0]));

                                }
                                else res.end(JSON.stringify('Wrong password'));

                            }else {res.end(JSON.stringify('Error'));}

                });

            })

            app.post('/shopcard/',(req,res,next)=>{

                var post_data = req.body;
                var idCli = post_data.idcli;

                con.query('select * from Orders where IdCli=?',[idCli],function(err,result,fields){
                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                    });
                            if(result && result.length){
                                res.json(result);
                            }else {res.end('noproducts');}

                });

            })

            app.post('/gid/',(req,res,next)=>{

                var post_data = req.body;
                var email = post_data.email;

                con.query('select * from Users where email=?',[email],function(err,result,fields){
                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                    });
                            if(result && result.length){
                                res.json(result);
                            }else {res.end('no user');}

                });

            })

app.post('/mqte/',(req , res , next)=>{

                var post_data = req.body;
                var id = post_data.id;
                var qte =  post_data.qte;
                con.query('SELECT qte FROM `Products` where idimg=? '
                ,[id], function(err, result, fields){

                    con.on('error',function(err){
                        console.log('[MySQL ERROR]',err);
                        res.json('Contact error : ', err);
                    });
                        var x;
                        for (var i of result) {
                            x=i.qte;
                            break ;
                        }
                        var m = x-qte;
                        con.query('UPDATE `Products` SET qte=? where idimg=? '
                                    ,[m,id], function(err, result, fields){

                                        con.on('error',function(err){
                                            console.log('[MySQL ERROR]',err);
                                        });
                                    });
                                    res.end('done')
                });


})


//start server

app.listen(3000, () => {
    console.log('running on port 3000');
})