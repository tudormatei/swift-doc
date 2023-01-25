import React, { useState, useEffect, useSelector } from 'react';
import axios, { Axios } from "axios";
import { Navigate } from 'react-router-dom';

import './FileUpload.css';
import { images } from '../../constants';

const UPLOAD_ENDPOINT = "http://127.0.0.1:8080/api/v1/document";

const FileUpload = () => {
  const [file, setFile] = useState(null);
  const [originalFileName, setFileName] = useState("");
  const [response, setResponse] = useState(null);
  const [imgs, setImages] = useState([]);

  const handleSubmit = async (event) => {
    setFileName("");
    setImages([]);
    event.preventDefault();

    const email = sessionStorage.getItem('loggedUser');

    const formData = new FormData();
    formData.append("doc", file);
    formData.append("email", email);

    setFileName(file.name);
    await axios.post(UPLOAD_ENDPOINT, formData, {
      responseType: 'blob',
      headers: {
        "content-type": "multipart/form-data",
      },
    }).then((response) => {
      setResponse(response.data);
      getImages();
    })
    .catch((error) => console.log(error));
  };
  
  const downloadDocument = () => {
    const b = new Blob([response]);
    const url = window.URL.createObjectURL(b);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', originalFileName);
    document.body.appendChild(link);
    link.click();
  }

  const getImages = async () => {
    try {
      const response = await axios.get(UPLOAD_ENDPOINT);
      const images = response.data;
      setImages(images.map(image => `data:image/png;base64,${image}`));
    } catch (error) {
        console.log(error);
    }
  };

  return (
    <div className="upload w-100">
      {!sessionStorage.getItem('loggedIn') ? <Navigate replace to="/login" /> : null}
      <div className="d-flex w-100 flex-row align-items-center uploadButtons">
        <div class="input-group w-50 justify-content-end">
          <div className="d-flex flex-column w-50 uploadButton">
            <input type="file" class="form-control mx-3" onChange={(e) => setFile(e.target.files[0])}/>
            <button type="submit" onClick={handleSubmit} class="btn w-50 m-3">Upload</button>
          </div>
        </div>
        {response != null ? (
          <>
            <div className="w-50 d-flex justify-content-start">
              <div class="card">
                <div class="card-body downloadButton">
                  <h5 class="card-title">Document is ready!</h5>
                  <button onClick={downloadDocument} class="btn w-50 m-2">Download</button>
                </div>
              </div>
            </div>
          </>
        ): null}
      </div>
      {imgs != null && response != null? (
        <>
          <div class="flex-row justify-content-center imgDiv">
            {imgs.map((image, index) => (
              <img class="img-fluid m-3" key={index} src={image} alt="Document Image"/>
            ))}
          </div>
        </>
      ) : null}
    </div>
  )
}

export default FileUpload