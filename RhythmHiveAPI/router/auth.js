const express = require('express');
const {User, validate} = require('../models/user');
const nodemailer = require('nodemailer');
const fs = require('fs');

const router = express.Router();
router.post('/login', async (req, res) => {
    let user = {
        username: req.body.username,
        password: req.body.password
    };


    let checkUser = await User.findOne({ username: user.username });

    if(!checkUser){
        return res.status(200).json({
            message: "User doesn't Exists",
            code: "false"
        });
    }

    if(checkUser.password !== user.password){
        return res.status(200).json({
            message: "Wrong Password!",
            code: "false"
        });
    }

    return res.status(200).json({
        message: "User successfully logged in!",
        code: "true"
    });

});

router.post('/', async (req, res) => {
    let user = {
        name: req.body.name,
        username: req.body.username,
        email: req.body.email,
        password: req.body.password
    };
    cpassword = req.body.cpassword;

    //Validation Check
    const { error } = validate(user);
    if(error){
        return res.status(200).json({
            message: "Validation Error!",
            code: "val_error"
        });
    }
    //Passowrd Check
    if(cpassword !== user.password){
        return res.status(200).json({
            message: "Passwords don't match!",
            code: "val_error"
        });
    }

    //Check Existing Account
    let checkUser1 = await User.findOne({ email: user.email });
    let checkUser2 = await User.findOne({ username: user.username });

    if(checkUser1 || checkUser2){
        return res.status(200).json({
            message: "User Already Exists",
            code: "val_error"
        });
    }
    var token = Math.random();

    let newUser = new User({
        name: user.name,
        username: user.username,
        email: user.email,
        password: user.password,
        verification: token
    });

    // Email Verification
    
    let transporter = nodemailer.createTransport({
        // host: 'smtp.hotmail.com',
        // port: 465,
        // secure: true, 
        service: 'hotmail',
        auth: {
            user: "mujeebarshad@hotmail.com", // generated ethereal user
            pass: "Magnet123" // generated ethereal password
        }
    });

       const mailOptions = {
        from: 'mujeebarshad@hotmail.com', // sender address
        to: 'mujeebarshad62@gmail.com', // list of receivers
        subject: 'Verification', // Subject line
        html: '<p>http://localhost:3000/verify?user='+user.username+'&token='+token+'</p>'// plain text body
      };

      transporter.sendMail(mailOptions, function (err, info) {
        if(err)
          console.log(err);
        else
          console.log(info);
     });

    // /Email Verification

    const result = await newUser.save();
    
    if (result) {
        return res.status(200).json({
            message: "User has been added!",
            code: "req_success"
        });
    }
});

router.post('/verify', async(req, res) => {
    var username = req.param('user');
    var token = req.param('token');

    let getUser = await User.findOne({ username:username });
    if(getUser.verification === token){
        getUser.verification = "true";
        const result = await getUser.save();
        return res.status(200).json({
            message: "Email Verified!",
            code: "req_success"
        });
    }
    return res.status(200).json({
        message: "Error Verifying Email!",
        code: "req_fail"
    });
});

//Get Video File

router.post('/getvideo', async(req, res) => {
    console.log("WORKING!");
    var file = req.body.videoUri;
    //console.log(file);
    fs.writeFile("E:/FYP/Emotion Detector/moodVideo.mp4", new Buffer.from(file, 'base64'), function(err) {
        if(err) {
            return console.log(err);
        }
    
        console.log("The video file was saved!");
    }); 
    return res.status(200).json({
        message: "Working!",
        code: "req_fail"
    });
    
});


module.exports = router;