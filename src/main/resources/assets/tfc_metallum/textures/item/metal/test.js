const fs = require('fs')

let arr = []

for(let s of fs.readdirSync('.')) {
    if(s.endsWith('.js')) {
        continue
    }
    arr.push({
        name:s,
        'isToolArmor': false
    })
}

console.log(arr)