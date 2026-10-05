import {copyFileSync,readFileSync,writeFileSync} from 'node:fs';
import {fileURLToPath} from 'node:url';
import {resolve,dirname} from 'node:path';
const root=resolve(dirname(fileURLToPath(import.meta.url)),'..');
const assets=resolve(root,'android/app/src/main/assets');
for(const name of ['app.js','engine.js','guitar.js'])copyFileSync(resolve(root,name),resolve(assets,name));
const html=readFileSync(resolve(root,'index.html'),'utf8').replace(/<link rel="(?:apple-touch-icon|icon|manifest)"[^>]*>/g,'');
writeFileSync(resolve(assets,'index.html'),html);
console.log('Shared web code synchronized to Android assets.');
