import os

import numpy as np
from flask import (Flask, Response, jsonify, request, send_file,
                   send_from_directory)
from PyPDF2 import PdfReader

UPLOAD_FOLDER = './/backend//documents//'

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/')
def index():
    return '<h1>FLASK APP IS RUNNING!</h1>'


@app.route('/api/pass', methods=['POST'])
def modifyPDF():
    try:
        pdf_file = request.files['doc']
        pdf_name = pdf_file.filename

        pdf_file.save(os.path.join(app.config['UPLOAD_FOLDER'] + pdf_name))

        file_path = os.path.join(app.config['UPLOAD_FOLDER'] + pdf_name)
        
        reader = PdfReader(file_path)
        page = reader.pages[0]
        print(page.extract_text())

        return send_file('documents//' + pdf_name)

    except Exception as e:
        return "Error occured"

if __name__ == '__main__':
    app.run(debug=True)