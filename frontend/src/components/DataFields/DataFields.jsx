import React, { useState, useEffect, useSelector } from 'react';
import axios, { Axios } from "axios";
import { Navigate } from 'react-router-dom';

import './DataFields.css';
import { images } from '../../constants';

const UPDATE_ENDPOINT = "http://127.0.0.1:8080/api/v1/auth/updateData";
const GET_ENDPOINT = "http://127.0.0.1:8080/api/v1/auth/getData";
const DataFields = () => {
  const getEmail = () => {
    if(sessionStorage.getItem('loggedUser') != ""){
      return sessionStorage.getItem('loggedUser');
    }

    return localStorage.getItem('loggedUser');
  };

  const getUserInfo = async (event) => {
    event.preventDefault();
    const email = getEmail();

    const formData = new FormData();
    
    formData.append("email", getEmail());
    const resp = await axios.post(GET_ENDPOINT, formData, {
      headers: {
        "content-type": "multipart/form-data",
      },
    }).then((response) => {
      console.log(response.data);
      const data = response.data;
      if(data != null){
        setFirstName(data["fname"]);
        setLastName(data["lname"]);
        setMiddleName(data["mname"]);
        setStreet(data["street"]);
        setStreetNumber(data["streetNr"]);
        setBlock(data["block"]);
        setStair(data["stair"]);
        setFloorNumber(data["floorNr"]);
        setApartmentNumber(data["apartmentNr"]);
        setCity(data["city"]);
        setCounty(data["county"]);
        setZipcode(data["zipcode"]);
        setCountry(data["country"]);
        setPhoneNumber(data["phoneNr"]);
        setSocialSecurityNumber(data["socialSecurityNr"]);
      }
    })
    .catch((error) => console.log(error));
  }

  const [updateStatus, setUpdateStatus] = useState(false);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [middleName, setMiddleName] = useState("");
  const [street, setStreet] = useState("");
  const [streetNumber, setStreetNumber] = useState("");
  const [block, setBlock] = useState("");
  const [stair, setStair] = useState("");
  const [floorNumber, setFloorNumber] = useState("");
  const [apartmentNumber, setApartmentNumber] = useState("");
  const [city, setCity] = useState("");
  const [county, setCounty] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [country, setCountry] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [socialSecurityNumber, setSocialSecurityNumber] = useState("");

  const handleSubmit = async (event) => {
    setUpdateStatus(false);
    event.preventDefault();
    const formData = new FormData();
    
    formData.append("email", getEmail());
    formData.append("fname", firstName);
    formData.append("lname", lastName);
    formData.append("mname", middleName);
    formData.append("street", street);
    formData.append("streetNr", streetNumber);
    formData.append("block", block);
    formData.append("stair", stair);
    formData.append("floorNr", floorNumber);
    formData.append("apartmentNr", apartmentNumber);
    formData.append("city", city);
    formData.append("county", county);
    formData.append("zipcode", zipcode);
    formData.append("country", country);
    formData.append("phoneNr", phoneNumber);
    formData.append("socialSecurityNr", socialSecurityNumber);
    const resp = await axios.post(UPDATE_ENDPOINT, formData, {
      headers: {
        "content-type": "multipart/form-data",
      },
    }).then((response) => {
      console.log(response.data);
      setUpdateStatus(response.data);
    })
    .catch((error) => console.log(error));
  };

  return (
    <form>
        {!sessionStorage.getItem('loggedIn') ? <Navigate replace to="/login" /> : null}
        <div className="datafields w-100 d-flex flex-column justify-content-center align-items-center text-center">
            <div class="w-50 text-center ">
                <h1>Hello, {getEmail()}</h1>
            </div>
            <button type="submit" onClick={getUserInfo} class="btn my-4">Show Current Info</button>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">First Name</label>
                <input type="text" class="form-control" placeholder={firstName} id="exampleInputName1" onChange={(e) => setFirstName(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Last Name</label>
                <input type="text" class="form-control" placeholder={lastName} id="exampleInputName1" onChange={(e) => setLastName(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Middle Name</label>
                <input type="text" class="form-control" placeholder={middleName} id="exampleInputName1" onChange={(e) => setMiddleName(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Street</label>
                <input type="text" class="form-control" placeholder={street} id="exampleInputName1" onChange={(e) => setStreet(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Street Number</label>
                <input type="text" class="form-control" placeholder={streetNumber} id="exampleInputName1" onChange={(e) => setStreetNumber(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Block</label>
                <input type="text" class="form-control" placeholder={block} id="exampleInputName1" onChange={(e) => setBlock(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Stair</label>
                <input type="text" class="form-control" placeholder={stair} id="exampleInputName1" onChange={(e) => setStair(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Floor Number</label>
                <input type="text" class="form-control" placeholder={floorNumber} id="exampleInputName1" onChange={(e) => setFloorNumber(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Apartment Number</label>
                <input type="text" class="form-control" placeholder={apartmentNumber} id="exampleInputName1" onChange={(e) => setApartmentNumber(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">City</label>
                <input type="text" class="form-control" placeholder={city} id="exampleInputName1" onChange={(e) => setCity(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">County</label>
                <input type="text" class="form-control" placeholder={county} id="exampleInputName1" onChange={(e) => setCounty(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Zip Code</label>
                <input type="text" class="form-control" placeholder={zipcode} id="exampleInputName1" onChange={(e) => setZipcode(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Country</label>
                <input type="text" class="form-control" placeholder={country} id="exampleInputName1" onChange={(e) => setCountry(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Phone Number</label>
                <input type="text" class="form-control" placeholder={phoneNumber} id="exampleInputName1" onChange={(e) => setPhoneNumber(e.target.value)}/>
            </div>
            <div class="mb-3 w-25">
                <label for="exampleInputName1" class="form-label">Social Security Number</label>
                <input type="text" class="form-control" placeholder={socialSecurityNumber}  id="exampleInputName1" onChange={(e) => setSocialSecurityNumber(e.target.value)}/>
            </div>
            <button type="submit" onClick={handleSubmit} class="btn">Update</button>
        </div>
    </form>
  )
}

export default DataFields