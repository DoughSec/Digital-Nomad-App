<template>
    <header class="app-header">
         <div class="logo-container">
             <img src="../../public/img/SoleCoIMG.jpg" alt="SoleCo Photo" class="logo">
             <h1 class="app-title">Ray's Hair</h1>            
         </div>
     </header>
     <link rel="preconnect" href="https://fonts.googleapis.com">
     <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
     <link href="https://fonts.googleapis.com/css2?family=Monoton&family=Rammetto+One&display=swap" rel="stylesheet">
 </template>
 
 <script>
 export default {
     name: 'SiteHeader'
 }
 </script>
 
 <style scoped>
    .app-header {
        background-color: #97A97C;
        color: white;
        display: flex;
        align-items: center;
        height: 100%;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        /* width: 100vw; */
    }
    .app-title {
        display: flex;
        justify-items: center;
        font-size: 5vw;
        font-family: "Rammetto One", sans-serif;
        font-weight: 400;
        font-style: normal;
    }

    .logo-container {
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        width: 100vw;
        height: 50vh;
    }

    .logo {
        height: 30vh;
        width: 30vw;
        margin-right: 10px;
    }
    @media only screen and (min-width: 1024px) { 
        .logo-container {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            width: 98vw;
            height: 50vh;
        }

        .logo {
            height: 30vh;
            width: 30vw;
            margin-right: 10px;
        }
    }

    @media only screen and (max-width: 425px) { 
        .logo {
            height: 30vh;
            width: 30vw;
            margin-right: 10px;
        }
        .logo-container {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            height: 50vh;
        }
    }

 </style>