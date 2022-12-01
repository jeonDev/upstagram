// string 공백 여부 체크
const isNotEmpty = (str) => {
    return str === null || str === 'undefined' || str === '' ? true : false
}

export default {
    isNotEmpty
}