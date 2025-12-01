export interface Burger {
  name: string;
  price: string;
  desc: string;
  img: string;
}

export const BURGERS: Burger[] = [
  { name: 'Hamburguesa Extrema', price: '25.90', desc: 'Doble carne, doble queso y mucho tocino para los que buscan intensidad.', img: 'Multimedia/burguer1.webp' },
  { name: 'Hamburguesa Parrillera', price: '23.90', desc: 'Sabor a parrilla con chimichurri y un toque ahumado.', img: 'Multimedia/PARRILLERA.webp' },
  { name: 'Hamburguesa La Carretillera', price: '23.90', desc: 'Receta tradicional con una mezcla especial de condimentos.', img: 'Multimedia/CARRETILLERA.webp' },
  { name: 'Hamburguesa Mexicana', price: '21.90', desc: 'Con guacamole, jalapeños y totopos para darle crocancia.', img: 'Multimedia/MEXICANA.webp' },
  { name: 'Hamburguesa La Tumbay', price: '21.90', desc: 'Huevo frito, papas crocantes y salsa casera.', img: 'Multimedia/TUMBAY.webp' },
  { name: 'Hamburguesa A lo Pobre', price: '21.90', desc: 'Sabor tradicional con huevo frito y papas.', img: 'Multimedia/POBRE.webp' },
  { name: 'Hamburguesa Hawaiana', price: '21.90', desc: 'Dulce piña a la parrilla y jamon ahumado.', img: 'Multimedia/HAWUAIANA.webp' },
  { name: 'Hamburguesa Royal', price: '20.90', desc: 'Tu hamburguesa favorita con un toque de elegancia.', img: 'Multimedia/ROYAL.webp' },
  { name: 'Hamburguesa Queso Tocino', price: '20.90', desc: 'Doble queso y tocino para los amantes del sabor intenso.', img: 'Multimedia/QUESO TOCINO.webp' },
  { name: 'Hamburguesa Cheese', price: '19.90', desc: 'Extra queso fundido en cada mordida.', img: 'Multimedia/CHEESE.webp' },
  { name: 'Hamburguesa Clasica', price: '17.90', desc: 'La clásica: carne, queso, lechuga y tomate.', img: 'Multimedia/CLASICA.webp' },
  { name: 'Hamburguesa La Churrita', price: '29.90', desc: 'Ingredientes premium y toppings exclusivos.', img: 'Multimedia/CHURRITA.webp' }
];

export function getBurger(name: string): Burger | undefined {
  return BURGERS.find(b => b.name === name);
}
