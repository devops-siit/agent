import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient ,HttpParams} from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { User } from '../../model/User';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    private headers = new HttpHeaders({'Content-Type': 'application/json'});

    constructor(
        private http: HttpClient
    ) { }

    login(auth: any): Observable<any> {
        return this.http.post(`${environment.baseUrl}/${environment.login}`,
         {username: auth.username, password: auth.password}, {headers: this.headers, responseType: 'json'});
    }

    isLoggedIn(): boolean {
        if (!localStorage.getItem('user')) {
                return false;
        }
        return true;
    }

    register(user: User): Observable<any> {
        return this.http.post(`${environment.baseUrl}/${environment.signUp}`, user, {headers: this.headers, responseType: 'json'});
    }


    signOut(): Observable<any> {
        return this.http.get(`${environment.baseUrl}/${environment.signOut}`, {headers: this.headers});

    }

    changePassword(passwordData: any): Observable<any> {
        return this.http.post(`${environment.baseUrl}/${environment.changePassword}`,
         {oldPassword: passwordData.oldPassword, newPassword: passwordData.newPassword}, {headers: this.headers, responseType: 'json'});
    }


    signUp(data: any):  Observable<any> {
		return this.http.post(`${environment.baseUrl}/${environment.signUp}`, {firstName: data.firstName,
			lastName: data.lastName, username: data.email, password: data.password}, {headers: this.headers, responseType: 'json'});
	
	}

    validateToken(): Observable<any>{
        const item = localStorage.getItem('user');

        if (item) {
            const decodedItem = JSON.parse(item);
            let queryParams = {};
                queryParams = {
                headers: this.headers,
                observe: 'response',
                params: new HttpParams()
                    .set('token', String(decodedItem.token)),
                
        };
        return this.http.get(`${environment.authUrl}/validate-token`, queryParams).pipe(map(res => res));
          
    }
    else {
        throw console.error();
        
    }
}

}
