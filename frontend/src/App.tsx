import './App.scss'
import { Footer } from './sections/footer/Footer'
import { Header } from './sections/header/Header'
import { Skills } from './sections/skills/Skills'
import { Tabs } from './sections/tabs/Tabs'
import { Work } from './sections/work/Work'

function App() {
  return (
    <div className="max-w-5xl mx-auto px-4 py-12 md:py-20 space-y-8">
      <Header />
      <Tabs />
      <Skills />
      <Work />
      <Footer />
    </div>
  )
}

export default App
