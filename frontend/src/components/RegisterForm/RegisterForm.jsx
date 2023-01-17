import React, { useState, useEffect, useSelector } from 'react';
import axios, { Axios } from "axios";
import { Navigate } from 'react-router-dom';

import './RegisterForm.css';
import { images } from '../../constants';

const UPLOAD_ENDPOINT = "http://127.0.0.1:8080/api/v1/auth/register";
const RegisterForm = () => {

  const [email, setEmail] = useState("");
  const [pass, setPass] = useState("");
  const [registerStatus, setRegisterStatus] = useState(false);
  const [rememberStatus, setRememberStatus] = useState(null);

  const handleChange = () => setRememberStatus(current => !current);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData();
    setRegisterStatus(false);
    
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
      setRegisterStatus(response.data);
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
    <form className="register d-flex flex-column justify-content-center align-items-center text-center">
      {sessionStorage.getItem('loggedIn') || registerStatus ? <Navigate replace to="/profile" /> : null}
    <div class="mb-3">
        <label for="exampleInputEmail1" class="form-label">Email address</label>
        <input type="email" class="form-control" onChange={(e) => setEmail(e.target.value)} id="exampleInputEmail1" aria-describedby="emailHelp"/>
    </div>
    <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">Password</label>
        <input type="password" class="form-control" onChange={(e) => setPass(e.target.value)} id="exampleInputPassword1"/>
    </div>
    <div class="mb-3 form-check">
          <input type="checkbox" class="form-check-input" value={rememberStatus} onChange={handleChange} id="exampleCheck1"/>
          <label class="form-check-label" for="exampleCheck1" onChange={(e) => setRememberStatus(e.target.value)}>Remember me</label>
      </div>
    <button type="submit" class="btn" onClick={handleSubmit}>Register</button>
    </form>
  )
}

export default RegisterForm