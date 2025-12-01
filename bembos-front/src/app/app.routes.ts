import { Routes } from '@angular/router';
import { CatalogComponent } from './components/catalog/catalog.component';
import { BurgerDetailComponent } from './components/catalog/burger-detail.component';
import { BuilderComponent } from './components/builder/builder.component';
import { OrderComponent } from './components/order/order.component';
import { ShareComponent } from './components/share/share.component';
import { RankingComponent } from './components/ranking/ranking.component';
import { LoginComponent } from './components/auth/login.component';
import { ProjectComponent } from './components/project/project.component';
import { AuthGuard } from './guards/auth.guard';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { RegisterComponent } from './components/auth/register.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'catalog', pathMatch: 'full' },
	{ path: 'catalog', component: CatalogComponent },
	{ path: 'catalog/:name', component: BurgerDetailComponent },
	{ path: 'build', component: BuilderComponent, canActivate: [AuthGuard] },
	{ path: 'order', component: OrderComponent, canActivate: [AuthGuard] },
	{ path: 'share', component: ShareComponent, canActivate: [AuthGuard] },
	{ path: 'ranking', component: RankingComponent, canActivate: [AuthGuard] },
	{ path: 'login', component: LoginComponent },
	{ path: 'project', component: ProjectComponent, canActivate: [AuthGuard] },
	{ path: 'welcome', component: WelcomeComponent },
	{ path: 'register', component: RegisterComponent }
];
