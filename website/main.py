from flask import Flask, render_template
app = Flask(__name__)

@app.route('/')
def main():
    return render_template('index.html')

@app.route('/data')
def show_data():
    return render_template('testdata.csv')
