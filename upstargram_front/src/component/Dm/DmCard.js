const DmCard = (props) => {
    const {dm, id} = props;

    return (
        <div className={dm.sendId === id ? 'my-text-div' : 'friend-text-div'}>
            <div className={dm.sendId === id ? 'my-text-container' : 'friend-text-div'}>
                <div className={dm.sendId === id ? 'my-text' : 'friend-text'}>{ dm.message }</div>
            </div>
        </div>
    )
}

export default DmCard;