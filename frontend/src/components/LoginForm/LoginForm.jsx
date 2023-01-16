import React, { useState, useEffect, useSelector } from 'react';
import axios, { Axios } from "axios";
import { Navigate } from 'react-router-dom';

import './LoginForm.css';
import { images } from '../../constants';

const UPLOAD_ENDPOINT = "http://127.0.0.1:8080/api/v1/auth/login";
const LoginForm = () => {

  const [email, setEmail] = useState("");
  const [pass, setPass] = useState("");
  const [loginStatus, setLoginStatus] = useState(false);
  const [rememberStatus, setRememberStatus] = useState(false);

  const handleChange = () => setRememberStatus(current => !current);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData();
    
    console.log(email);
    console.log(pass);
    formData.append("email", email);
    formData.append("pass", pass);
    const resp = await axios.post(UPLOAD_ENDPOINT, formData, {
      headers: {
        "content-type": "multipart/form-data",
      },
    }).then((response) => {
      console.log(response.data);
      setLoginStatus(response.data);

      if(rememberStatus) {
        localStorage.setItem('loggedIn', response.data);
        if(response.data) {
          localStorage.setItem('loggedUser', email);
        }
        else{
          localStorage.setItem('loggedUser', '');
        }
      }
      else{
        sessionStorage.setItem('loggedIn', response.data);
        if(response.data) {
          sessionStorage.setItem('loggedUser', email);
        }
        else{
          sessionStorage.setItem('loggedUser', '');
        }
      }
    })
    .catch((error) => console.log(error));
  };

  return (
    <form className="login w-100 d-flex flex-column justify-content-center align-items-center text-center">
      {sessionStorage.getItem('loggedIn') || loginStatus ? <Navigate replace to="/profile" /> : null}
      <div class="mb-3">
          <label for="exampleInputEmail1" class="form-label">Email address</label>
          <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" onChange={(e) => setEmail(e.target.value)}/>
      </div>
      <div class="mb-3">
          <label for="exampleInputPassword1" class="form-label">Password</label>
          <input type="password" class="form-control" id="exampleInputPassword1" onChange={(e) => setPass(e.target.value)}/>
      </div>
      <div class="mb-3 form-check">
          <input type="checkbox" class="form-check-input" value={rememberStatus} onChange={handleChange} id="exampleCheck1"/>
          <label class="form-check-label" for="exampleCheck1" >Remember me</label>
      </div>
      <button type="submit" class="btn" onClick={handleSubmit}>Login</button>
      <div class="mb-3 mt-3">
        Don't have an account? Register <a href="/register" className="link-primary">here</a>
      </div>
    </form>
  )
}

export default LoginForm