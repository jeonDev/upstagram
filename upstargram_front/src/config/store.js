import { createStore } from "redux";

const userInfo = {
    id : '',
    name : '',
    nickname : '',
    sex : '',
    tel : ''
}

const rootReducer = (state = userInfo, action) => {
    switch (action.type) {
        case "SET_USER" :
            console.log("SET_USER : " + action.payload)
            return {
                ...state,
                id:action.payload.id,
                name:action.payload.name,
                nickname:action.payload.nickname,
                sex:action.payload.sex,
                tel:action.payload.tel
            };
        default:
            return state;
    }
};

const store = createStore(rootReducer);

export default store;