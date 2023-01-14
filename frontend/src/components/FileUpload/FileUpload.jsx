import React, { useState, useEffect, useSelector } from 'react';
import axios, { Axios } from "axios";

import './FileUpload.css';
import { images } from '../../constants';

const UPLOAD_ENDPOINT = "http://127.0.0.1:8080/api/v1/document";

const FileUpload = () => {
  const [file, setFile] = useState(null);
  const [originalFileName, setFileName] = useState("");
  const [response, setResponse] = useState(null);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData();
    formData.append("doc", file);
    console.log(file.name);
    setFileName(file.name);
    const resp = await axios.post(UPLOAD_ENDPOINT, formData, {
      responseType: 'blob',
      headers: {
        "content-type": "multipart/form-data",
      },
    }).then((response) => {
      console.log(response.data);
      setResponse(response.data);
    })
    .catch((error) => console.log(error));
  };
  
  const downloadDocument = () => {
    const b = new Blob([response])
    console.log(b.size);
    const url = window.URL.createObjectURL(b);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', originalFileName);
    document.body.appendChild(link);
    link.click();
  }

  return (
    <div className="upload w-100">
      <div className="d-flex w-100 flex-row align-items-center">
        <div class="input-group w-50 justify-content-start">
          <div className="d-flex flex-column w-50 uploadButton">
            <input type="file" class="form-control mx-3" onChange={(e) => setFile(e.target.files[0])}/>
            <button type="submit" onClick={handleSubmit} class="btn w-50 m-3">Upload</button>
          </div>
        </div>
        {response != null ? (
          <>
            <div className="w-50 d-flex justify-content-end">
        <div class="card">
          <img class="card-img-top" src="" alt="Image of document"/>
          <div class="card-body">
            <h5 class="card-title">Document is ready!</h5>
            <button onClick={downloadDocument} class="btn btn-primary w-50 m-3">Download</button>
          </div>
        </div>
        </div>
          </>
        ): null}
      </div>
    </div>
  )
}

export default FileUpload