import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
      <div id="header"class="d-flex bd-highlight">
        <a href="/" class="p-2 flex-grow-1 bd-highlight">Board Service</a>
        <form action="/posts/search" method="GET" class="form-inline p-2 bd-highlight" role="search">
        <input type="text" name="keyword" class="form-control" id="search" placeholder="검색"/>
        <button class="btn btn-success bi bi-search"></button>
        </form>
        </div>
        <nav id ="nav">
        <div class="text-right">
        <span class="mx-3"></span>
        <a href="/logout" class="btn btn-outline-dark">로그아웃</a>
        <a href="/modify" class="btn btn-outline-dark bi bi-gear"></a>
        <a href="/oauth2/authorization/google" role="button" class="btn btn-outline-danger bi bi-google"> 로그인</a>
        <a href="/auth/login" role="button" class="btn btn-outline-dark bi bi-lock-fill"> 로그인</a>
        <a href="/auth/join" role="button" class="btn btn-outline-dark bi bi-person-circle"> 회원가입</a>
        </div></nav>
    </div>
  );
}

export default App;
