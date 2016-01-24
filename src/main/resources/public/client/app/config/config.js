var RECENT_LIMIT = 10;
var HOST = "http://localhost";//'http://t2siebeltestaut.t2srv.corp.tele2.com/';
var APP = '/client/app/';
var TEAM = ['zanezaka', 'martozol', 'edijvalu', 'jekasoko', 'krisbirzi', 'anasdoro', 'zaneergl', 'sanimeln', 'jakhashr', 'sergkovt'];
var ALL_SOAP_JOBS = HOST + "/api/soap/all";
var TOOLS = [
    {
        name: 'Data Pool',
        src: './img/datapool.png',
        href: 'datapool/',
        description: 'Take generated data from here and use it'
    },
    {
        name: 'Sanity',
        src: './img/sanity.png',
        href: 'sanity/',
        description: 'This tool is for tracking sanity runs'
    },
    {
        name: 'Defects Tool',
        src: './img/defects.png',
        href: 'defects/Client/app/index.html',
        description: 'Track recent defects from HP ALM'
    },
    {
        name: 'KPI',
        src: './img/kpi.png',
        href: 'monitor/',
        description: 'Ususal Managment stuff!'
    }
]

var ENV = ['AGNIA', 'MOLLY', 'SUE', 'AMOS'];


