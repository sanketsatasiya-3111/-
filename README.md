<!DOCTYPE html>
<html lang="gu">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Jayveer બ્રહ્માંડ 🌌</title>

<style>
*{box-sizing:border-box}

body{
    margin:0;
    background:#020617;
    color:white;
    font-family:Arial,sans-serif;
    overflow-x:hidden;
}

.hero{
    min-height:100vh;
    display:flex;
    flex-direction:column;
    align-items:center;
    justify-content:center;
    text-align:center;
    background:radial-gradient(circle,#172554,#020617 70%);
}

.hero h1{
    font-size:clamp(38px,7vw,75px);
    color:#67e8f9;
    text-shadow:0 0 25px #38bdf8;
}

.hero p{
    font-size:20px;
    color:#cbd5e1;
}

button{
    border:0;
    border-radius:30px;
    padding:13px 24px;
    background:#6366f1;
    color:white;
    font-size:17px;
    cursor:pointer;
    margin:8px;
}

nav{
    position:sticky;
    top:0;
    z-index:50;
    padding:15px;
    text-align:center;
    background:#020617;
    border-bottom:1px solid #334155;
}

nav a{
    color:white;
    text-decoration:none;
    margin:0 12px;
}

section{
    padding:60px 5%;
}

.title{
    text-align:center;
    color:#67e8f9;
    font-size:40px;
}

/* SOLAR SYSTEM */

.space{
    position:relative;
    width:min(900px,95vw);
    height:min(900px,95vw);
    margin:30px auto;
    border-radius:50%;
    background:
      radial-gradient(circle,#111827,#020617 70%);
    overflow:hidden;
}

.sun{
    position:absolute;
    width:75px;
    height:75px;
    left:50%;
    top:50%;
    transform:translate(-50%,-50%);
    border-radius:50%;
    background:#facc15;
    box-shadow:0 0 50px #f59e0b;
    display:flex;
    justify-content:center;
    align-items:center;
    color:#111;
    font-weight:bold;
    z-index:20;
}

/* ORBITS */

.orbit{
    position:absolute;
    left:50%;
    top:50%;
    transform:translate(-50%,-50%);
    border:1px solid rgba(255,255,255,.25);
    border-radius:50%;
    animation:orbit linear infinite;
}

.orbit1{width:13%;height:13%;animation-duration:5s}
.orbit2{width:23%;height:23%;animation-duration:7s}
.orbit3{width:33%;height:33%;animation-duration:9s}
.orbit4{width:43%;height:43%;animation-duration:12s}
.orbit5{width:56%;height:56%;animation-duration:17s}
.orbit6{width:68%;height:68%;animation-duration:22s}
.orbit7{width:80%;height:80%;animation-duration:28s}
.orbit8{width:92%;height:92%;animation-duration:34s}

@keyframes orbit{
    from{
        transform:translate(-50%,-50%) rotate(0deg);
    }
    to{
        transform:translate(-50%,-50%) rotate(360deg);
    }
}

/* PLANETS */

.planet{
    position:absolute;
    right:-18px;
    top:50%;
    transform:translateY(-50%);
    border-radius:50%;
    cursor:pointer;
    z-index:100;
    display:flex;
    justify-content:center;
    align-items:center;
    font-weight:bold;
    color:white;
    user-select:none;
    -webkit-tap-highlight-color:transparent;
}

.planet:hover{
    filter:brightness(1.4);
}

.mercury{
    width:28px;
    height:28px;
    background:#9ca3af;
}

.venus{
    width:35px;
    height:35px;
    background:#d97706;
}

.earth{
    width:40px;
    height:40px;
    background:#2563eb;
}

.mars{
    width:34px;
    height:34px;
    background:#dc2626;
}

.jupiter{
    width:65px;
    height:65px;
    background:#b45309;
}

.saturn{
    width:55px;
    height:55px;
    background:#d6b36a;
}

.uranus{
    width:48px;
    height:48px;
    background:#67e8f9;
}

.neptune{
    width:46px;
    height:46px;
    background:#1d4ed8;
}

/* INFO PANEL */

#info{
    max-width:850px;
    margin:30px auto;
    padding:30px;
    background:#0f172a;
    border:2px solid #334155;
    border-radius:20px;
    min-height:200px;
}

#info h2{
    color:#67e8f9;
    font-size:32px;
}

#info p{
    color:#cbd5e1;
    line-height:1.9;
    font-size:17px;
}

/* MOBILE */

@media(max-width:600px){

    .space{
        width:94vw;
        height:94vw;
    }

    .sun{
        width:55px;
        height:55px;
        font-size:11px;
    }

    .planet{
        right:-13px;
    }

    .title{
        font-size:30px;
    }
}
</style>
</head>

<body>

<div class="hero">

    <div style="font-size:70px">🌌</div>

    <h1>Welcome to Jayveer બ્રહ્માંડ</h1>

    <p>🚀 Explore Our Solar System 🚀</p>

    <button onclick="document.getElementById('solar').scrollIntoView()">
        🔭 Explore Universe
    </button>

</div>

<nav>
    <a href="#solar">🌌 Solar System</a>
    <a href="#info">📚 Information</a>
</nav>

<section id="solar">

<h2 class="title">☀️ ફરતું Solar System</h2>

<p style="text-align:center;color:#cbd5e1">
👇 કોઈપણ ગ્રહ પર CLICK / TAP કરો
</p>

<div class="space">

    <div class="sun">☀️</div>

    <div class="orbit orbit1">
        <div class="planet mercury" data-planet="mercury">☿</div>
    </div>

    <div class="orbit orbit2">
        <div class="planet venus" data-planet="venus">♀</div>
    </div>

    <div class="orbit orbit3">
        <div class="planet earth" data-planet="earth">🌍</div>
    </div>

    <div class="orbit orbit4">
        <div class="planet mars" data-planet="mars">♂</div>
    </div>

    <div class="orbit orbit5">
        <div class="planet jupiter" data-planet="jupiter">♃</div>
    </div>

    <div class="orbit orbit6">
        <div class="planet saturn" data-planet="saturn">♄</div>
    </div>

    <div class="orbit orbit7">
        <div class="planet uranus" data-planet="uranus">♅</div>
    </div>

    <div class="orbit orbit8">
        <div class="planet neptune" data-planet="neptune">♆</div>
    </div>

</div>

<div id="info">

    <h2>🪐 ગ્રહ પસંદ કરો</h2>

    <p>
    ઉપર ફરતા કોઈપણ ગ્રહ પર Click કરો.
    અહીં તે ગ્રહની સંપૂર્ણ માહિતી દેખાશે.
    </p>

</div>

</section>

<script>

const data = {

mercury:{
name:"☿ બુધ — Mercury",
info:"બુધ સૂર્યની સૌથી નજીકનો ગ્રહ છે અને સૂર્યમંડળનો સૌથી નાનો ગ્રહ છે. સૂર્યથી તેનું સરેરાશ અંતર લગભગ 57.9 મિલિયન km છે. તેનો વ્યાસ લગભગ 4,879 km છે. બુધને સૂર્યની એક પરિક્રમા પૂર્ણ કરવામાં લગભગ 88 પૃથ્વી દિવસ લાગે છે. તેના કોઈ કુદરતી ઉપગ્રહ નથી."
},

venus:{
name:"♀ શુક્ર — Venus",
info:"શુક્ર સૂર્યથી બીજો ગ્રહ છે અને સૂર્યમંડળનો સૌથી ગરમ ગ્રહ છે. તેનું સરેરાશ અંતર લગભગ 108.2 મિલિયન km છે. તેનો વ્યાસ લગભગ 12,104 km છે. શુક્રનું એક વર્ષ લગભગ 225 પૃથ્વી દિવસનું છે. તેનો કોઈ ચંદ્ર નથી."
},

earth:{
name:"🌍 પૃથ્વી — Earth",
info:"પૃથ્વી આપણું ઘર છે અને હાલમાં જાણીતું જીવન ધરાવતો એકમાત્ર ગ્રહ છે. તે સૂર્યથી સરેરાશ 149.6 મિલિયન km દૂર છે. તેનો વ્યાસ લગભગ 12,742 km છે. પૃથ્વીનું એક વર્ષ લગભગ 365.25 દિવસનું છે અને તેનો એક કુદરતી ઉપગ્રહ છે — ચંદ્ર."
},

mars:{
name:"🔴 મંગળ — Mars",
info:"મંગળને તેની લાલ રંગની સપાટીને કારણે લાલ ગ્રહ કહેવામાં આવે છે. તે સૂર્યથી સરેરાશ 227.9 મિલિયન km દૂર છે. તેનો વ્યાસ લગભગ 6,779 km છે. મંગળનો એક દિવસ લગભગ 24.6 કલાકનો છે અને તેનું એક વર્ષ લગભગ 687 પૃથ્વી દિવસનું છે. તેના બે નાના ઉપગ્રહ છે."
},

jupiter:{
name:"🟤 ગુરુ — Jupiter",
info:"ગુરુ સૂર્યમંડળનો સૌથી મોટો ગ્રહ છે. તે એક Gas Giant છે. સૂર્યથી તેનું સરેરાશ અંતર લગભગ 778.5 મિલિયન km છે અને તેનો વ્યાસ લગભગ 139,820 km છે. ગુરુનો એક દિવસ લગભગ 10 કલાકનો છે. તેનું એક વર્ષ લગભગ 11.86 પૃથ્વી વર્ષનું છે."
},

saturn:{
name:"🪐 શનિ — Saturn",
info:"શનિ એક Gas Giant છે અને તેના સુંદર rings માટે પ્રખ્યાત છે. તે સૂર્યથી સરેરાશ 1.43 અબજ km દૂર છે. તેનો વ્યાસ લગભગ 116,460 km છે. શનિનો એક દિવસ લગભગ 10.7 કલાકનો છે અને તેનું એક વર્ષ લગભગ 29.5 પૃથ્વી વર્ષનું છે."
},

uranus:{
name:"🔵 યુરેનસ — Uranus",
info:"યુરેનસ એક Ice Giant છે. તેની ધરી ખૂબ જ ઢળેલી છે. તે સૂર્યથી સરેરાશ 2.87 અબજ km દૂર છે. તેનો વ્યાસ લગભગ 50,724 km છે. યુરેનસને સૂર્યની એક પરિક્રમા પૂર્ણ કરવામાં લગભગ 84 પૃથ્વી વર્ષ લાગે છે."
},

neptune:{
name:"🔵 નેપ્ચ્યુન — Neptune",
info:"નેપ્ચ્યુન સૂર્યથી સૌથી દૂરનો આઠમો મુખ્ય ગ્રહ છે. તે એક Ice Giant છે. સૂર્યથી તેનું સરેરાશ અંતર લગભગ 4.5 અબજ km છે. તેનો વ્યાસ લગભગ 49,244 km છે. તેને સૂર્યની એક પરિક્રમા પૂર્ણ કરવામાં લગભગ 165 પૃથ્વી વર્ષ લાગે છે."
}

};

/* દરેક ગ્રહ પર CLICK */

document.querySelectorAll(".planet").forEach(function(planet){

    planet.addEventListener("click",function(event){

        event.stopPropagation();

        const name = planet.getAttribute("data-planet");

        const selected = data[name];

        document.getElementById("info").innerHTML =

        "<h2>" + selected.name + "</h2>" +

        "<p>" + selected.info + "</p>";

        document.getElementById("info").scrollIntoView({
            behavior:"smooth",
            block:"center"
        });

    });

});

</script>

</body>
</html>
