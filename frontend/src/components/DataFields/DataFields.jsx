import React, { useState, useEffect, useSelector } from 'react';
import axios, { Axios } from "axios";
import { Navigate } from 'react-router-dom';

import './DataFields.css';
import { images } from '../../constants';

const DataFields = () => {
  const logOut = () => {
    // localStorage.setItem('loggedIn', false);
    // localStorage.setItem('loggedUser', '');
    // sessionStorage.setItem('loggedUser', '');
    // sessionStorage.setItem('loggedIn', false);
  };

  const getEmail = () => {
    if(sessionStorage.getItem('loggedUser') != ""){
      return sessionStorage.getItem('loggedUser');
    }

    return localStorage.getItem('loggedUser');
  };

  return (
    <form>
        {!sessionStorage.getItem('loggedIn') ? <Navigate replace to="/login" /> : null}
        <div className="datafields w-100 d-flex flex-column justify-content-center align-items-center text-center">
            <div class="w-50 text-center ">
                <h1>Hello, {getEmail()}</h1>
            </div>
            <button type="submit" class="btn my-4" onClick={logOut()}>Log out</button>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">First Name</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Last Name</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Adress</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">City</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">County</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Zip Code</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Country</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Phone Number</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">C.I. Number</label>
                <input type="text" class="form-control" id="exampleInputName1"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputEmail1" class="form-label">Email address</label>
                <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputPassword1" class="form-label">Password</label>
                <input type="password" class="form-control" id="exampleInputPassword1"/>
            </div>
            <button type="submit" class="btn">Update</button>
        </div>
    </form>
  )
}

export default DataFields