/** @type {import('tailwindcss').Config} */
module.exports = {
   content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        gray: {
          light: "#f2f2f2",
          dark: "rgba(49,47,50)",
          icon: 'rgb(156,163,175)'
        },
        yellow: {
          light: "#fabf43",
          dark: "#fab82f"
        },
        blue: {
          light: "#5890f6",
          dark: "#4684f5"
        }
      },
      boxShadow: {
        around: '0px 5px 20px 10px rgb(0 0 0 / 0.25)',
        aroundLess: '0px 5px 20px 10px rgb(0 0 0 / 0.1)',
        big: 'inset 0 3px 5px 0 rgba(0,0,0,0.1)'
      },
    },
  },
  plugins: [],
}
