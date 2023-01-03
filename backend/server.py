import os

from flask import Flask, Response, jsonify, request, send_file
from processes import modifyPDF

UPLOAD_FOLDER = './/backend//documents//'

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/')
def index():
    return '<h1>FLASK APP IS RUNNING!</h1>'


@app.route('/api/pass', methods=['POST'])
def loadPDF():
    pdf_file = request.files['doc']
    pdf_name = pdf_file.filename

    pdf_file.save(os.path.join(app.config['UPLOAD_FOLDER'] + pdf_name))

    file_path = os.path.join(app.config['UPLOAD_FOLDER'] + pdf_name)
    
    modifyPDF(file_path, pdf_name)

    return send_file('documents//' + pdf_name)


if __name__ == '__main__':
    app.run(debug=True)