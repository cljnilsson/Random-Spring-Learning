const
    path = require("path"),
    exec = require("child_process").exec,
    fs = require("fs");

const watchDir = path.join(__dirname, "/src");

let files  = [];
function findAllFilesInDirectory(directory) {
    fs.readdirSync(directory).forEach(File => {
        const absolute = path.join(directory, File);
        if (fs.statSync(absolute).isDirectory()) return findAllFilesInDirectory(absolute);
        else return files.push(absolute);
    });
}
findAllFilesInDirectory(watchDir);

let watching = true;
for(let f of files) {
    console.log(f)
    fs.watch(f, (e, file) => {
        if(!watching) {
            return;
        }
        watching = false;

        exec("mvn compile", function (error, stdout, stderr) {
            if (error !== null) {
                 console.log('exec error: ' + error);
            }
        });

        setTimeout(() => {
            watching = true;
        }, 300);
    });
}