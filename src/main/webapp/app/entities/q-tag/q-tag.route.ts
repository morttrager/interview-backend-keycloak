import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQTag, QTag } from 'app/shared/model/q-tag.model';
import { QTagService } from './q-tag.service';
import { QTagComponent } from './q-tag.component';
import { QTagDetailComponent } from './q-tag-detail.component';
import { QTagUpdateComponent } from './q-tag-update.component';

@Injectable({ providedIn: 'root' })
export class QTagResolve implements Resolve<IQTag> {
  constructor(private service: QTagService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQTag> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((qTag: HttpResponse<QTag>) => {
          if (qTag.body) {
            return of(qTag.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QTag());
  }
}

export const qTagRoute: Routes = [
  {
    path: '',
    component: QTagComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QTags',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QTagDetailComponent,
    resolve: {
      qTag: QTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QTags',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QTagUpdateComponent,
    resolve: {
      qTag: QTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QTags',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QTagUpdateComponent,
    resolve: {
      qTag: QTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QTags',
    },
    canActivate: [UserRouteAccessService],
  },
];
