import { Card } from "react-bootstrap";

const DmCard = (props) => {
    const {dm} = props;
    // TODO: 아이디 비교해서 메시지 누가보냈는지 체크
    return (
        <div className={dm.sendId ? 'my-text-div' : 'friend-text-div'}>
            <div className={dm.sendId ? 'my-text-container' : 'friend-text-div'}>
                <div className={dm.sendId ? 'my-text' : 'friend-text'}>{ dm.message }</div>
            </div>
        </div>
    )
}

export default DmCard;